package com.example.widgetapp

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.app.PendingIntent.getBroadcast
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.provider.SyncStateContract.Constants
import android.widget.RemoteViews
import java.util.Random

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in [NewAppWidgetConfigureActivity]
 */
class NewAppWidget : AppWidgetProvider() {
    //@SuppressLint("RemoteViewLayout")
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        //There may be multiple widgets active, so update all of them
        //for (appWidgetId in appWidgetIds) {
            val actionBtn = R.id.actionButton
            val textView = R.id.textView
            val count = appWidgetIds.count()
            for (i in 0 until count){

                val widgetId = appWidgetIds[i]
                val number = String.format("%03d", Random().nextInt(900) + 100)

                val remoteViews = RemoteViews(
                    context.packageName,
                    R.layout.new_app_widget
                )

                remoteViews.setTextViewText(textView, number)

                val intent = Intent(context, NewAppWidget::class.java)
                intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds)

                val pendingIntent = getBroadcast(
                    context, 0, intent, FLAG_MUTABLE
                )
                pendingIntent.apply { FLAG_UPDATE_CURRENT }

                remoteViews.setOnClickPendingIntent(actionBtn, pendingIntent)
                appWidgetManager.updateAppWidget(widgetId, remoteViews)
                //updateAppWidget(context, appWidgetManager)
                //updateAppWidget(context, appWidgetManager, widgetId)
        }
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        // When the user deletes the widget, delete the preference associated with it.
        for (appWidgetId in appWidgetIds) {
            deleteTitlePref(context, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}



internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int,
    appWidgetIds: IntArray
) {
    val widgetText = loadTitlePref(context, appWidgetId)
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.new_app_widget)
    views.setTextViewText(R.id.appwidget_text, widgetText)

    // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

