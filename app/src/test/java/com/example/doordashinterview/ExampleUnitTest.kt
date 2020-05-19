package com.example.doordashinterview

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.doordashinterview.data.Business
import com.example.doordashinterview.data.DoorDashRestaurantAPIResponse
import com.example.doordashinterview.data.RestaurantsNetworkDataSource
import com.example.doordashinterview.data.toRestaurantUIModel
import com.example.doordashinterview.view.ListRestaurantsViewModel
import com.example.doordashinterview.view.RestaurantUIModel
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()

        testDispatcher.cleanupTestCoroutines()
    }

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()


    @Test
    fun `test load initial`() {
        runBlocking {
            //given

            val restaurantsNetworkDataSource = mockk<RestaurantsNetworkDataSource>() {
                coEvery { getListRestaurants(any(), any()) } returns listOf(
                    DoorDashRestaurantAPIResponse(
                        id = 1,
                        status = "11 minutes",
                        business =  Business (
                            id = 2,
                            name =  "la mar"
                        ),
                        description = "peruvian food",
                        cover_img_url = "sss.png"
                    )
                )
            }

            val listRestaurantsViewModel = ListRestaurantsViewModel(restaurantsNetworkDataSource)
            val mockedObserver = createRestaurantListObserver()
            listRestaurantsViewModel.liveData.observeForever(mockedObserver)

            //when
            listRestaurantsViewModel.loadInitial()

            //then
            val slot = slot<List<RestaurantUIModel>>()

            verify { mockedObserver.onChanged(capture(slot)) }
            assertEquals(slot.captured[0].status,  "11 minutes")
        }

    }

    @Test
    fun `test load more does not get more data`() {
        runBlocking {
            //given
            val restaurantsNetworkDataSource = mockk<RestaurantsNetworkDataSource>() {
                coEvery { getListRestaurants(any(), any()) } returns listOf(
                    DoorDashRestaurantAPIResponse(
                        id = 1,
                        status = "11 minutes",
                        business =  Business (
                            id = 2,
                            name =  "la mar"
                        ),
                        description = "peruvian food",
                        cover_img_url = "sss.png"
                    )
                )
            }

            val listRestaurantsViewModel = ListRestaurantsViewModel(restaurantsNetworkDataSource)
            val mockedObserver = createRestaurantListObserver()
            listRestaurantsViewModel.liveData.observeForever(mockedObserver)

            //when
            listRestaurantsViewModel.loadMore(1, 40)

            //then
            val slot = slot<List<RestaurantUIModel>>()

            verify(exactly = 0) { mockedObserver.onChanged(capture(slot)) }
        }
    }

    @Test
    fun `test load more gets more data` () {
        runBlocking {
            //given
            val restaurantsNetworkDataSource = mockk<RestaurantsNetworkDataSource>() {
                coEvery { getListRestaurants(any(), any()) } returns listOf(
                    DoorDashRestaurantAPIResponse(
                        id = 1,
                        status = "11 minutes",
                        business =  Business (
                            id = 2,
                            name =  "la mar"
                        ),
                        description = "peruvian food",
                        cover_img_url = "sss.png"
                    )
                )
            }

            val listRestaurantsViewModel = ListRestaurantsViewModel(restaurantsNetworkDataSource)
            val mockedObserver = createRestaurantListObserver()
            listRestaurantsViewModel.liveData.observeForever(mockedObserver)
            
            //when
            listRestaurantsViewModel.loadMore(0, 20)

            //then
            val slot = slot<List<RestaurantUIModel>>()

            verify(exactly = 1) { mockedObserver.onChanged(capture(slot)) }
        }
    }

    @Test
    fun `verify DoorDashRestaurantAPIResponse is mapped to toRestaurantUIModel correctly  `() {
        val doorDashRestaurantAPIResponse = DoorDashRestaurantAPIResponse(
             id = 1,
             status = "11 minutes",
             business =  Business (
                 id = 2,
                 name =  "la mar"
            ),
            description = "peruvian food",
            cover_img_url = "sss.png"
        )

        val restaurantUImodel = doorDashRestaurantAPIResponse.toRestaurantUIModel()

        assertEquals("la mar", restaurantUImodel.title)
        assertEquals("peruvian food", restaurantUImodel.subtitle)
        assertEquals("sss.png", restaurantUImodel.imageUrl)
        assertEquals("11 minutes", restaurantUImodel.status)
    }

    private fun createRestaurantListObserver(): Observer<List<RestaurantUIModel>> =
        spyk(Observer { })
}
