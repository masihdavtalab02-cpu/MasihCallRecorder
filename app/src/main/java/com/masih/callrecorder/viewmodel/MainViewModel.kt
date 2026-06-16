package com.masih.callrecorder.viewmodel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.masih.callrecorder.CallRecorderApp
import com.masih.callrecorder.data.LicenseManager
import com.masih.callrecorder.data.Recording
import com.masih.callrecorder.util.AudioUtils
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val app = application as CallRecorderApp
    private val dao = app.database.recordingDao()
    val licenseManager = LicenseManager(app.prefsManager)
    val allRecordings: Flow<List<Recording>> = dao.getAllRecordings()
    private val _searchQuery = MutableStateFlow("")
    val filteredRecordings = _searchQuery.flatMapLatest { q -> if (q.isBlank()) dao.getAllRecordings() else dao.searchRecordings("%$q%") }
    fun setSearchQuery(query: String) { _searchQuery.value = query }
    fun deleteRecording(rec: Recording) { viewModelScope.launch { AudioUtils.deleteRecording(rec.filePath); dao.deleteRecording(rec) } }
    fun toggleStar(rec: Recording) { viewModelScope.launch { dao.updateRecording(rec.copy(isStarred = !rec.isStarred)) } }
    fun initializeLicense() { viewModelScope.launch { licenseManager.initialize() } }
    val isLicensed: Flow<Boolean> = flow { emit(licenseManager.isLicensed()) }
    val isPremium = app.prefsManager.isPremium
    val autoRecord = app.prefsManager.autoRecord
}