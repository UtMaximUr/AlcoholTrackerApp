package com.utmaximur.alcohol_calculator.interactor

import com.utmaximur.alcohol_calculator.models.CalculateResult
import com.utmaximur.alcohol_calculator.models.Drink
import com.utmaximur.alcohol_calculator.models.Gender
import com.utmaximur.domain.Interactor
import com.utmaximur.settingsManager.UserSettingsManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import org.koin.core.annotation.Factory
import kotlin.math.pow
import kotlin.math.round

@Factory
internal class BACCalculate(
    private val userSettingsManager: UserSettingsManager
) : Interactor<List<Drink>, Flow<CalculateResult>>() {

    override suspend fun doWork(params: List<Drink>): Flow<CalculateResult> =
        combine(
            userSettingsManager.heightStateStream,
            userSettingsManager.weightStateStream,
            userSettingsManager.genderStateStream
        ) { heightCm, weightKg, genderName ->
            calculateBAC(params, heightCm, weightKg, genderName)
        }

    /**
     * Выполняет расчёт показателей алкоголя
     * @param drinks Список напитков
     * @param heightCm Рост в см
     * @param weightKg Вес в кг
     * @param genderName Название пола
     */
    private fun calculateBAC(
        drinks: List<Drink>,
        heightCm: Int,
        weightKg: Int,
        genderName: String
    ): CalculateResult {
        // 1. Рассчёт общего количества алкоголя
        val totalAlcoholGrams = calculateTotalAlcoholGrams(drinks)

        // 2. Определение пола
        val gender = Gender.findGender(genderName)

        // 3. Рассчёт коэффициента редукции
        val reductionFactor = calculateReductionFactor(gender, weightKg, heightCm)

        // 4. Рассчёт концентрации алкоголя (BAC)
        val bac = calculateBACValue(totalAlcoholGrams, reductionFactor, weightKg)

        // 5. Рассчёт времени выведения
        val (hours, minutes) = calculateEliminationTime(bac)

        return CalculateResult(
            bac = bac.round(2),
            eliminationTimeHh = hours,
            eliminationTimeMm = minutes
        )
    }

    /**
     * Рассчитывает общее количество чистого алкоголя в граммах
     * @param drinks Список напитков
     */
    private fun calculateTotalAlcoholGrams(drinks: List<Drink>): Double {
        return drinks.sumOf { drink ->
            drink.volume * (drink.alcoholPercentage / 100) * ETHANOL_DENSITY
        }
    }

    /**
     * Рассчитывает коэффициент редукции (распределения алкоголя)
     * @param gender Пол
     * @param weightKg Вес в кг
     * @param heightCm Рост в см
     */
    private fun calculateReductionFactor(
        gender: Gender,
        weightKg: Int,
        heightCm: Int
    ): Double {
        return when (gender) {
            Gender.MALE -> MALE_REDUCTION_FACTOR_FORMULA(weightKg, heightCm)
            Gender.FEMALE -> FEMALE_REDUCTION_FACTOR_FORMULA(weightKg, heightCm)
            Gender.NONE -> { // Усреднённое значение
                (MALE_REDUCTION_FACTOR_FORMULA(weightKg, heightCm) +
                        FEMALE_REDUCTION_FACTOR_FORMULA(weightKg, heightCm)) / 2
            }
        }
    }

    /**
     * Рассчитывает концентрацию алкоголя в крови (BAC)
     * @param alcoholGrams Общее количество алкоголя в граммах
     * @param reductionFactor Коэффициент редукции
     * @param weightKg Вес в кг
     */
    private fun calculateBACValue(
        alcoholGrams: Double,
        reductionFactor: Double,
        weightKg: Int
    ): Double {
        val bac = alcoholGrams / (reductionFactor * weightKg)
        return maxOf(0.0, bac)
    }

    /**
     * Рассчитывает время выведения алкоголя
     * @param bac Концентрация алкоголя в крови
     */
    private fun calculateEliminationTime(bac: Double): Pair<Int, Int> {
        val eliminationHours = maxOf(0.0, bac) / METABOLISM_RATE
        return formatEliminationTime(eliminationHours)
    }

    /**
     * Округляет Double до указанного количества знаков
     * @param decimals Количество знаков после запятой
     */
    private fun Double.round(decimals: Int): Double {
        val multiplier = 10.0.pow(decimals)
        return round(this * multiplier) / multiplier
    }

    /**
     * Форматирует время выведения в часы и минуты
     * @param hours Время в часах
     */
    private fun formatEliminationTime(hours: Double): Pair<Int, Int> {
        val totalMinutes = (hours * 60).toInt()
        return (totalMinutes / 60) to (totalMinutes % 60)
    }

    companion object {
        private const val ETHANOL_DENSITY = 0.789 // г/мл
        private const val METABOLISM_RATE = 0.15 // ‰/час

        // Формулы коэффициента редукции
        private val MALE_REDUCTION_FACTOR_FORMULA: (Int, Int) -> Double =
            { weight, height -> 0.31608 - 0.004821 * weight + 0.004632 * height }

        private val FEMALE_REDUCTION_FACTOR_FORMULA: (Int, Int) -> Double =
            { weight, height -> 0.31223 - 0.006446 * weight + 0.004466 * height }
    }
}