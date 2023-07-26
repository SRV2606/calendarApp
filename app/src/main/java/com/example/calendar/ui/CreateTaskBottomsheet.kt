package com.example.calendar.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.calendar.R
import com.example.calendar.databinding.BottomsheetTaskEntryBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CreateTaskBottomsheet : BottomSheetDialogFragment() {

    private lateinit var onSubmissionListener: ((title: String, description: String) -> Unit)

    private lateinit var binding: BottomsheetTaskEntryBinding

    companion object {
        const val TAG = "CreateTaskBottomSheet"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.bottomsheet_task_entry, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.actionCTA.setOnClickListener {
            val title = binding.titleET.text.toString()
            val description = binding.descET.text.toString()
            onSubmissionListener(title, description)
            dismiss()
        }
    }

    fun setOnSubmissionListener(listener: (title: String, description: String) -> Unit) {
        onSubmissionListener = listener
    }
}
