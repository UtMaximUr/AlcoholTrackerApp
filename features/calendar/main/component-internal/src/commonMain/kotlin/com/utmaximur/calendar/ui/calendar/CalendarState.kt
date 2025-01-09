package com.utmaximur.calendar.ui.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kotlinx.datetime.Clock
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.number
import kotlinx.datetime.todayIn
import com.utmaximur.calendar.models.CalendarDateInfo
import com.utmaximur.calendar.models.CalendarMonth
import com.utmaximur.calendar.models.atDay
import com.utmaximur.calendar.models.daysOfWeekSortedBy
import com.utmaximur.calendar.models.endRangeMonth
import com.utmaximur.calendar.models.firstDayOfWeek
import com.utmaximur.calendar.models.getByIndex
import com.utmaximur.calendar.models.indexOf
import com.utmaximur.calendar.models.lastDayOfWeek
import com.utmaximur.calendar.models.next
import com.utmaximur.calendar.models.previous
import com.utmaximur.calendar.models.startRangeMonth

/**
 * Создает и запоминает состояние календаря в рамках композиции с использованием
 * механизма сохранения состояния.
 *
 * Этот метод возвращает объект [CalendarState], инициализируя его с указанным
 * представлением календаря. При первом вызове метод сохраняет состояние, чтобы
 * сохранить его при пересоздании композиции.
 *
 * @param selectedDate , Опциональный параметр, который задает начальную выбранную дату.
 *                       По умолчанию это текущая дата в системном часовом поясе.
 * @return Объект [CalendarState], содержащий текущее состояние представления календаря.
 */
@Composable
fun rememberCalendarState(
    selectedDate: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault())
): CalendarState {
    return rememberSaveable(
        inputs = arrayOf(
            selectedDate
        ),
        saver = CalendarState.Saver,
    ) {
        CalendarState(selectedDate = selectedDate)
    }
}

@Stable
class CalendarState internal constructor(
    selectedDate: LocalDate
) {
    private val dayOfWeekAmount: Int = 7
    private var _currentMonthYear by mutableStateOf(selectedDate.CalendarMonth)
    private var _selectedDate by mutableStateOf(selectedDate)
    private val startMonth by mutableStateOf(_currentMonthYear.startRangeMonth())
    private val endMonth by mutableStateOf(_currentMonthYear.endRangeMonth())
    private val range by lazy { startMonth..endMonth }

    /**
     * Получает список объектов [CalendarDateInfo] для месяцев от текущего года на год вниз и вверх.
     *
     * @return Список объектов [CalendarDateInfo], представляющих календарные месяцы.
     */
    val months: List<CalendarDateInfo>
        get() = buildList {
            for (year in startMonth.year..endMonth.year) {
                Month.entries.map { month ->
                    val calendarMonth = CalendarMonth(year = year, month = month)
                    add(calculateCalendarGridInfo(calendarMonth))
                }
            }
        }

    /**
     * Вычисляет и получает список дат для текущего года.
     *
     * @return Список объектов [LocalDate], представляющих даты текущего года.
     */
    val dates = calculateDates(_selectedDate.year)

    /**
     * Разбивает список дат на недели.
     *
     * @return Список списков, где каждый подсписок представляет собой неделю.
     */
    val datesByWeek = dates.chunked(dayOfWeekAmount)

    /**
     * Получает текущую выбранную дату.
     *
     * @return Объект [LocalDate], представляющий текущую выбранную дату.
     */
    val currentDate = Clock.System.todayIn(TimeZone.currentSystemDefault())

    /**
     * Получает выбранную дату.
     *
     * @return Объект [LocalDate], представляющий выбранную дату.
     */
    val selectedDate: LocalDate
        get() = _selectedDate

    val currentMonthYear: CalendarMonth
        get() = _currentMonthYear

    /**
     * Получает номер текущей недели на основе выбранной даты.
     *
     * @return [Int], номер недели, в которой находится [selectedDate].
     */
    val weekNumber: Int
        get() {
            val findDates = datesByWeek.find { it.contains(selectedDate) }
            return datesByWeek.indexOf(findDates)
        }

    /**
     * Получает первую дату из списка месяцев.
     *
     * @return Объект [LocalDate], представляющий первую дату в первом месяце.
     */
    val firstDate: LocalDate
        get() = months[currentMonthIndex].dateMatrix.flatten().first()

    /**
     * Получает индекс текущего месяца в диапазоне.
     *
     * @return [Int], индекс текущего месяца в диапазоне.
     */
    val currentMonthIndex: Int
        get() = range.indexOf(_currentMonthYear) ?: 0

    /**
     * Выбирает вид календаря по указанной дате.
     *
     * @param date Дата, которая будет установлена как выбранная.
     */
    fun selectViewByDate(date: LocalDate) {
        _selectedDate = date
    }

    /**
     * Прокрутка к месяцу по индексу.
     *
     * @param index Индекс месяца для прокрутки.
     */
    fun scrollToMonth(index: Int) {
        val calendarMonth = range.getByIndex(index)
        _currentMonthYear = calendarMonth
    }

    /**
     * Прокрутка к дате по индексу.
     *
     * @param index Индекс даты для прокрутки.
     */
    fun scrollToDate(index: Int) {
        val monthNumber = dates[index].monthNumber
        if (_currentMonthYear.month.number != monthNumber) {
            _currentMonthYear = dates[index].CalendarMonth
        }
        _selectedDate = dates[index]
    }

    /**
     * Вычисляет информацию о датах для заданного месяца и возвращает объект [CalendarDateInfo].
     *
     * Метод разбивает даты на матрицу, организованную по неделям, исходя из количества дней в неделе.
     *
     * @param currentMonth Объект [CalendarMonth], для которого будет рассчитана информация о датах.
     * @return Объект [CalendarDateInfo], содержащий матрицу дат и данные о текущем месяце.
     */
    private fun calculateCalendarGridInfo(
        currentMonth: CalendarMonth
    ): CalendarDateInfo {
        val datesByMonth = calculateDatesByMonth(currentMonth)
        return CalendarDateInfo(
            dateMatrix = datesByMonth.chunked(dayOfWeekAmount),
            currentMonth = currentMonth
        )
    }

    /**
     * Вычисляет список дат для указанного года.
     *
     * Метод создает объекты [CalendarMonth] для каждого месяца данного года и
     * собирает все соответствующие даты в виде списка.
     *
     * @param year Целое число, представляющее год для вычисления дат.
     * @return Список объектов [LocalDate], представляющий все даты в указанном году.
     */
    private fun calculateDates(year: Int): List<LocalDate> {
        val months = Month.entries.map { month ->
            CalendarMonth(year, month)
        }
        val setDates = mutableSetOf<LocalDate>()
        months.forEach { currentMonth ->
            val datesByMonth = calculateDatesByMonth(currentMonth)
            setDates.addAll(datesByMonth)
        }
        return setDates.toList()
    }

    /**
     * Вычисляет список дат для указанного месяца.
     *
     * Метод формирует список дат, который включает дни из предыдущего, текущего и следующего месяцев,
     * чтобы заполнить календарную сетку на 42 дня (6 недель).
     *
     * @param currentMonth Объект [CalendarMonth], для которого будет рассчитан список дат.
     * @return Список объектов [LocalDate], представляющий все дни в месяце, включая дни соседних месяцев.
     */
    private fun calculateDatesByMonth(
        currentMonth: CalendarMonth
    ): List<LocalDate> {
        val firstDayOfWeek = daysOfWeekSortedBy(firstDayOfWeek()).first()
        val previousMonth = currentMonth.previous()
        val nextMonth = currentMonth.next()
        val previousMonthLastDayOfWeek = previousMonth.lastDayOfWeek()
        val lastDaysAmountInPreviousMonth = when (firstDayOfWeek) {
            DayOfWeek.MONDAY -> previousMonthLastDayOfWeek.isoDayNumber
            DayOfWeek.SUNDAY -> {
                if (previousMonthLastDayOfWeek == DayOfWeek.SATURDAY) 0
                else previousMonthLastDayOfWeek.isoDayNumber + 1
            }

            else -> error("Unexpected firstDayOfWeek: $firstDayOfWeek")
        } % dayOfWeekAmount
        val daysAmountInCurrentMonth = currentMonth.numberOfDays
        val firstDaysAmountInNextMonth = (42 - lastDaysAmountInPreviousMonth - daysAmountInCurrentMonth)
        val dates = mutableListOf<LocalDate>()
        repeat(lastDaysAmountInPreviousMonth) {
            dates.add(
                previousMonth.atDay(
                    previousMonth.numberOfDays + it + 1 - lastDaysAmountInPreviousMonth
                )
            )
        }

        repeat(daysAmountInCurrentMonth) {
            dates.add(
                currentMonth.atDay(it + 1)
            )
        }

        repeat(firstDaysAmountInNextMonth) {
            dates.add(
                nextMonth.atDay(it + 1)
            )
        }
        return dates
    }

    companion object {
        internal val Saver: Saver<CalendarState, Any> = listSaver(
            save = {
                listOf(
                    it.selectedDate.toString()
                )
            },
            restore = {
                CalendarState(
                    selectedDate = LocalDate.parse(it[0])
                )
            }
        )
    }
}