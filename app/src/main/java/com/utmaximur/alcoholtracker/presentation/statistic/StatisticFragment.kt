package com.utmaximur.alcoholtracker.presentation.statistic

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.utmaximur.alcoholtracker.App
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.domain.entity.DrinkStatistic
import com.utmaximur.alcoholtracker.presentation.base.BaseViewModelFactory
import com.utmaximur.alcoholtracker.presentation.splash.ui.theme.AlcoholTrackerTheme
import com.utmaximur.alcoholtracker.util.getIdRaw
import java.util.*
import javax.inject.Inject

class StatisticFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: BaseViewModelFactory<StatisticViewModel>

    private val viewModel: StatisticViewModel by viewModels(
        factoryProducer = { viewModelFactory }
    )

    private fun injectDagger() {
        App.instance.alcoholTrackComponent.inject(this)
    }

    @ExperimentalPagerApi
    @ExperimentalFoundationApi
    @ExperimentalAnimationApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        injectDagger()
        return ComposeView(requireContext()).apply {
            setContent {
                AlcoholTrackerTheme {
                    Surface(color = MaterialTheme.colors.background) {
                        StatisticView(viewModel)
                    }
                }
            }
        }
    }
}


@ExperimentalPagerApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun StatisticView(viewModel: StatisticViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CountMoneyStatistic(viewModel)
        CountDaysStatistic(viewModel)
        CountDrinksStatistic(viewModel)
    }
}

@ExperimentalPagerApi
@Composable
fun CountMoneyStatistic(viewModel: StatisticViewModel) {

    val statisticsPriceByPeriod by viewModel.statisticsPriceByPeriod.observeAsState()

    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        elevation = 8.dp
    ) {
        Column {
            Text(
                modifier = Modifier
                    .padding(12.dp, 12.dp, 12.dp, 0.dp),
                text = stringResource(id = R.string.statistic_spent),
                fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
                color = colorResource(id = R.color.text_color)
            )
            Scaffold(
                modifier = Modifier.heightIn(32.dp, 96.dp),
                content = {
                    statisticsPriceByPeriod?.let { money -> ViewPagerCountMoney(money) }
                }
            )
        }
    }
}

@ExperimentalPagerApi
@Composable
fun CountDaysStatistic(viewModel: StatisticViewModel) {

    val statisticsCountDays by viewModel.statisticsCountDays.observeAsState()

    Card(
        modifier = Modifier
            .padding(12.dp),
        shape = RoundedCornerShape(22.dp),
        elevation = 8.dp
    ) {
        Scaffold(
            modifier = Modifier.heightIn(32.dp, 96.dp),
            content = {
                statisticsCountDays?.let { days -> ViewPagerCountDrink(days) }
            }
        )
    }
}

@ExperimentalFoundationApi
@Composable
fun CountDrinksStatistic(viewModel: StatisticViewModel) {

    val drinksList by viewModel.statisticsDrinksList.observeAsState()

    Card(
        modifier = Modifier.padding(12.dp),
        shape = RoundedCornerShape(22.dp),
        elevation = 8.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(start = 12.dp, top = 12.dp),
                text = stringResource(id = R.string.statistic_drinks),
                fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
                color = colorResource(id = R.color.text_color)
            )
            LazyVerticalGrid(
                modifier = Modifier.padding(12.dp),
                cells = GridCells.Fixed(4)
            ) {
                drinksList?.let { drinks ->
                    items(drinks.size) { index ->
                        DrinkItem(drinks[index])
                    }
                }
            }
        }
    }
}

@Composable
fun CountMoneyItem(money: String, period: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .padding(12.dp)
                .weight(1f),
            text = period,
            fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
            color = colorResource(id = R.color.text_color)
        )
        Text(
            modifier = Modifier.padding(bottom = 12.dp, top = 12.dp),
            text = money,
            fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
            color = colorResource(id = R.color.text_color)
        )
        Text(
            modifier = Modifier.padding(12.dp),
            text = stringResource(id = R.string.add_currency),
            fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
            color = colorResource(id = R.color.text_color)
        )
    }
}

@Composable
fun DrinksDayItem(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .padding(12.dp),
            text = text,
            fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
            color = colorResource(id = R.color.text_color)
        )
    }
}

@Composable
fun DrinkItem(
    drink: DrinkStatistic,
    context: Context = LocalContext.current
) {
    Column(
        modifier = Modifier.padding(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Image(

                modifier = Modifier.padding(4.dp),
                painter = painterResource(id = drink.icon.getIdRaw(context)),
                contentDescription = null
            )
            Text(
                modifier = Modifier.padding(4.dp),
                text = String.format(
                    context.resources.getString(R.string.statistic_count_drink),
                    drink.count
                ),
                fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
                color = colorResource(id = R.color.text_color)
            )
        }
        Text(
            text = drink.drink,
            fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
            color = colorResource(id = R.color.text_color)
        )
    }
}

@ExperimentalPagerApi
@Composable
fun ViewPagerCountMoney(
    money: List<String>,
    pagerState: PagerState = rememberPagerState(),
    tabItems: Array<String> = stringArrayResource(id = R.array.statistic_period_array)
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            modifier = Modifier.heightIn(32.dp, 64.dp),
            count = tabItems.size,
            state = pagerState
        ) { index ->
            CountMoneyItem(money[index], tabItems[index])
        }
        DotsIndicator(tabItems.size, pagerState)
    }
}

@ExperimentalPagerApi
@Composable
fun ViewPagerCountDrink(
    statistic: List<Int>,
    pagerState: PagerState = rememberPagerState(),
    context: Context = LocalContext.current
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            modifier = Modifier.heightIn(32.dp, 64.dp),
            count = statistic.size,
            state = pagerState
        ) { index ->
            if (index == 0) {
                DrinksDayItem(
                    String.format(
                        context.resources.getString(R.string.statistic_count_days),
                        context.resources.getQuantityString(
                            R.plurals.plurals_day,
                            statistic.first(),
                            statistic.first()
                        ),
                        Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_YEAR)
                    )
                )
            } else {
                DrinksDayItem(
                    String.format(
                        context.resources.getString(R.string.statistic_count_days_no_drink),
                        context.resources.getQuantityString(
                            R.plurals.plurals_day,
                            statistic.last(),
                            statistic.last()
                        )
                    )
                )
            }
        }
        DotsIndicator(statistic.size, pagerState)
    }
}

@ExperimentalPagerApi
@Composable
fun DotsIndicator(
    totalDots: Int,
    pagerState: PagerState
) {
    LazyRow(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .padding(bottom = 12.dp)

    ) {

        items(totalDots) { index ->
            if (index == pagerState.currentPage) {
                Image(
                    painter = painterResource(id = R.drawable.ic_dot_18dp),
                    contentDescription = null
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_dot_default_18dp),
                    contentDescription = null
                )
            }
            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}