package com.gb.testcontentproviderapp.ui.main

import android.content.ContentResolver
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.Build.ID
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.gb.testcontentproviderapp.R

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val contentUri = Uri.parse("content://weather.list.lesson9.provider/weather_entity_table")

        val contentResolver: ContentResolver = requireContext().contentResolver
        contentResolver.insert(contentUri,toContentValues(WeatherEntity()))
        // Отправляем запрос на получение контактов и получаем ответ в виде Cursor
        val cursorWithWeather: Cursor? = contentResolver.query(
             contentUri,
            null,
            null,
            null,
            "name ASC"
        )
        Log.d("@@@"," cursorWithWeather $cursorWithWeather")
        cursorWithWeather?.let { cursor->
            Log.d("@@@"," cursor ${cursor.count}")
            for(i in 0 until cursor.count){ // аналог 0..cursorWithContacts.count-1
                cursor.moveToPosition(i)
                val name = cursor.getString(cursor.getColumnIndex("name"))
                Log.d("@@@"," name $name")
            }
        }
        cursorWithWeather?.close()

    }


    private val ID = "id"
    private val NAME = "name"
    private val TEMPERATURE = "temperature"

    data class WeatherEntity(
        val id: Long = 0,
        val name: String = "srgedwrdhrtj",
        val temperature: Int = 0,
    )


    fun toContentValues(student: WeatherEntity): ContentValues {
        return ContentValues().apply {
            put(ID, student.id)
            put(NAME, student.name)
            put(TEMPERATURE, student.temperature)
        }
    }


}