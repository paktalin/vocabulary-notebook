package com.paktalin.vocabularynotebook

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val etWord: EditText = itemView.findViewById(R.id.etWord)
    val etTranslation: EditText = itemView.findViewById(R.id.etTranslation)
    val btnPopupMenu: ImageButton = itemView.findViewById(R.id.btnContextMenu)
    val layout: LinearLayout = itemView.findViewById(R.id.tableLayout)
}