package com.github.jackparisi.droidboxsample.activity

import android.databinding.ObservableField
import com.github.jackparisi.droidbox.architecture.model.DroidViewModel
import com.github.jackparisi.droidbox.architecture.model.data.DroidObservableData
import com.github.jackparisi.droidboxsample.database.games.Game
import com.github.jackparisi.droidboxsample.database.games.SteamGamesRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

/**
 * Created by Giacomo Parisi on 25/10/17.
 * https://github.com/JackParisi
 */

class HomeViewModel @Inject constructor(private val steamGamesRepository: SteamGamesRepository) : DroidViewModel() {

    var games = DroidObservableData<ObservableField<List<Game>>>(ObservableField(mutableListOf()))

    fun loadData() {
        showLoading()
        steamGamesRepository.dataProvider.repository
                .subscribeOn(Schedulers.io())
                .map {
                    val games = it.data?.gameList?.games
                    if (games != null) {
                        sortGameByName(games.toMutableList())
                    }
                    games ?: mutableListOf()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    games.data.set(it)
                    hideLoading()
                }) { showError(it) }
    }

    private fun sortGameByName(list: MutableList<Game>) {

        Collections.sort(list, { game1, game2 ->
            if (game1.name != null && game2.name != null)
                game1.name.toLowerCase().compareTo(game2.name.toLowerCase())
            else
                0
        })
    }
}