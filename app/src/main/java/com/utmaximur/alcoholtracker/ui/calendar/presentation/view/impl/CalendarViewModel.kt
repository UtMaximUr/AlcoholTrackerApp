package com.utmaximur.alcoholtracker.ui.calendar.presentation.view.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
import com.utmaximur.alcoholtracker.data.storage.`object`.AlcoholTrackerRealmObject
import io.realm.Realm
import io.realm.RealmResults


class CalendarViewModel: ViewModel() {

//    private var mDb: Realm? = null
//    private var mLoansResult: LiveData<String?>? = null
//
//    fun CalendarViewModel() {
//        mDb = Realm.getDefaultInstance()
//        subscribeToMikesLoansSinceYesterday()
//        simulateDataUpdates()
//    }
//
//    fun simulateDataUpdates() {
//        DatabaseInitializer.populateAsync(mDb)
//    }
//
//    fun getLoansResult(): LiveData<String?>? {
//        return mLoansResult
//    }
//
//    private fun subscribeToMikesLoansSinceYesterday() {
//        val loans: LiveRealmData<AlcoholTrackerRealmObject> = alcoholTrackModel(mDb)
//            .findLoansByNameAfter("Mike", getYesterdayDate())
//        mLoansResult =
//            Transformations.map(
//                loans,
//                object : Function<RealmResults<AlcoholTrackerRealmObject?>?, String?>() {
//                    fun apply(loans: RealmResults<Loan>): String? {
//                        val sb = StringBuilder()
//                        val simpleDateFormat = SimpleDateFormat(
//                            "yyyy-MM-dd HH:mm",
//                            Locale.US
//                        )
//                        for (loan in loans) {
//                            sb.append(
//                                java.lang.String.format(
//                                    "%s\n  (Returned: %s)\n",
//                                    loan.book.title,
//                                    simpleDateFormat.format(loan.endTime)
//                                )
//                            )
//                        }
//                        return sb.toString()
//                    }
//                })
//    }
//
//    /**
//     * This method will be called when this ViewModel is no longer used and will be destroyed.
//     *
//     *
//     * It is useful when ViewModel observes some data and you need to clear this subscription to
//     * prevent a leak of this ViewModel... Like RealmResults and the instance of Realm!
//     */
//    override fun onCleared() {
//        mDb!!.close()
//        super.onCleared()
//    }
//
//    private fun getYesterdayDate(): Date? {
//        val calendar: Calendar = Calendar.getInstance()
//        calendar.set(Calendar.DATE, -1)
//        return calendar.getTime()
//    }
}