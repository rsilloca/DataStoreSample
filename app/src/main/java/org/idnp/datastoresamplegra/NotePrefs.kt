package org.idnp.datastoresamplegra

import android.graphics.Color
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.idnp.datastoresamplegra.models.User

class NotePrefs(
    private val dataStore: DataStore<Preferences>
) {

    suspend fun saveNoteUser(user: User) {
        dataStore.edit { preferences ->
            preferences[USER_NAME] = user.name
            preferences[USER_AGE] = user.age
            preferences[USER_PHONE] = user.phone
            preferences[USER_EMAIL] = user.email
        }
    }

    val getUser: Flow<User>
        get() = dataStore.data.map { preferences ->
            User(
                preferences[USER_NAME] ?: "No name",
                preferences[USER_AGE] ?: 0,
                preferences[USER_PHONE] ?: "No phone",
                preferences[USER_EMAIL] ?: "No email"
            )
        }

    suspend fun saveNoteBackgroundColor(noteBackgroundColor: String) {
        dataStore.edit { preferences ->
            preferences[BACKGROUND_COLOR] = noteBackgroundColor
        }
    }

    val backgroundColor: Flow<String>
        get() = dataStore.data.map { preferences ->
            preferences[BACKGROUND_COLOR] ?: Color.CYAN.toString()
        }

    companion object {
        val PREFS_NAME = "PREFS_NAME"
        private val BACKGROUND_COLOR = stringPreferencesKey("key_app_background_color")
        private val USER_NAME = stringPreferencesKey("key_app_user_name")
        private val USER_AGE = intPreferencesKey("key_app_user_age")
        private val USER_PHONE = stringPreferencesKey("key_app_user_phone")
        private val USER_EMAIL = stringPreferencesKey("key_app_user_email")
    }
}
