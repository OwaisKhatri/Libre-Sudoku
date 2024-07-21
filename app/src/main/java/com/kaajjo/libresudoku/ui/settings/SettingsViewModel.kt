package com.kaajjo.libresudoku.ui.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaajjo.libresudoku.data.database.AppDatabase
import com.kaajjo.libresudoku.data.datastore.AppSettingsManager
import com.kaajjo.libresudoku.data.datastore.ThemeSettingsManager
import com.kaajjo.libresudoku.data.datastore.TipCardsDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
@Inject constructor(
    private val settingsDataManager: AppSettingsManager,
    private val tipCardsDataStore: TipCardsDataStore,
    private val appDatabase: AppDatabase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    @Inject
    lateinit var appThemeDataStore: ThemeSettingsManager

    val launchedFromGame by mutableStateOf(savedStateHandle.get<Boolean>("fromGame"))
    var resetStatsDialog by mutableStateOf(false)

    var darkModeDialog by mutableStateOf(false)
    var inputMethodDialog by mutableStateOf(false)
    var mistakesDialog by mutableStateOf(false)
    var languagePickDialog by mutableStateOf(false)
    var dateFormatDialog by mutableStateOf(false)
    var customFormatDialog by mutableStateOf(false)

    val darkTheme by lazy {
        appThemeDataStore.darkTheme
    }

    fun updateDarkTheme(value: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            appThemeDataStore.setDarkTheme(value)
        }

    val dynamicColors by lazy {
        appThemeDataStore.dynamicColors
    }

    fun updateDynamicColors(enabled: Boolean) =
        viewModelScope.launch {
            appThemeDataStore.setDynamicColors(enabled)
        }

    val amoledBlack by lazy {
        appThemeDataStore.amoledBlack
    }

    fun updateAmoledBlack(enabled: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            appThemeDataStore.setAmoledBlack(enabled)
        }


    val mistakesLimit = settingsDataManager.mistakesLimit
    fun updateMistakesLimit(enabled: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            settingsDataManager.setMistakesLimit(enabled)
        }

    val timer = settingsDataManager.timerEnabled
    fun updateTimer(enabled: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            settingsDataManager.setTimer(enabled)
        }

    val canResetTimer = settingsDataManager.resetTimerEnabled
    fun updateCanResetTimer(enabled: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            settingsDataManager.setResetTimer(enabled)
        }

    val highlightIdentical = settingsDataManager.highlightIdentical
    fun updateHighlightIdentical(enabled: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            settingsDataManager.setSameValuesHighlight(enabled)
        }

    val disableHints = settingsDataManager.hintsDisabled
    fun updateHintDisabled(disabled: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsDataManager.setHintsDisabled(disabled)
        }
    }

    val remainingUse = settingsDataManager.remainingUse
    fun updateRemainingUse(enabled: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsDataManager.setRemainingUse(enabled)
        }
    }

    val autoEraseNotes = settingsDataManager.autoEraseNotes
    fun updateAutoEraseNotes(enabled: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsDataManager.setAutoEraseNotes(enabled)
        }
    }

    val highlightMistakes = settingsDataManager.highlightMistakes
    fun updateMistakesHighlight(index: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsDataManager.setHighlightMistakes(index)
        }
    }

    val inputMethod = settingsDataManager.inputMethod
    fun updateInputMethod(value: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsDataManager.setInputMethod(value)
        }
    }

    fun resetTipCards() {
        viewModelScope.launch {
            tipCardsDataStore.setStreakCard(true)
            tipCardsDataStore.setRecordCard(true)
        }
    }

    fun deleteAllTables() {
        viewModelScope.launch(Dispatchers.IO) {
            appDatabase.clearAllTables()
        }
    }

    val seedColor by lazy { appThemeDataStore.themeColorSeed }
    fun updateCurrentSeedColor(seedColor: Color) {
        viewModelScope.launch(Dispatchers.IO) {
            appThemeDataStore.setCurrentThemeColor(seedColor)
        }
    }

    val keepScreenOn = settingsDataManager.keepScreenOn
    fun updateKeepScreenOn(enabled: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsDataManager.setKeepScreenOn(enabled)
        }
    }


    val funKeyboardOverNum = settingsDataManager.funKeyboardOverNumbers
    fun updateFunKeyboardOverNum(enabled: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsDataManager.setFunKeyboardOverNum(enabled)
        }
    }

    val dateFormat = settingsDataManager.dateFormat
    fun updateDateFormat(format: String) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsDataManager.setDateFormat(format)
        }
    }

    val saveLastSelectedDifficultyType = settingsDataManager.saveSelectedGameDifficultyType
    fun updateSaveLastSelectedDifficultyType(enabled: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            settingsDataManager.setSaveSelectedGameDifficultyType(enabled)
        }

    fun checkCustomDateFormat(pattern: String): Boolean {
        return try {
            DateTimeFormatter.ofPattern(pattern)
            true
        } catch (e: Exception) {
            false
        }
    }

    val paletteStyle by lazy { appThemeDataStore.themePaletteStyle }
    fun updatePaletteStyle(index: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            appThemeDataStore.setPaletteStyle(ThemeSettingsManager.paletteStyles[index].first)
        }

    val isUserDefinedSeedColor by lazy { appThemeDataStore.isUserDefinedSeedColor }
    fun updateIsUserDefinedSeedColor(value: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            appThemeDataStore.setIsUserDefinedSeedColor(value)
        }
    }
}