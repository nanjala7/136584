package com.example.demapp

import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import org.checkerframework.checker.units.qual.s
import java.util.Calendar

class AddNewTask : BottomSheetDialogFragment() {

  //  private lateinit var setDueDate: TextView
    //private lateinit var mTaskEdit: EditText
   // private lateinit var mSaveBtn: Button
   // private lateinit var firestore: FirebaseFirestore
    private lateinit var context: Context
    private var dueDate: String = ""
    //private var id: String = ""
    //private var dueDateUpdate: String = ""

    companion object {
        fun newInstance(): AddNewTask {
            return AddNewTask()

        }

        const val TAG = "AddNewTask"


        }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.add_new_task, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val setDueDate: TextView = view.findViewById(R.id.set_due_tv)
        val mTaskEdit: EditText = view.findViewById(R.id.task_edittext)
        val mSaveBtn: Button = view.findViewById(R.id.save_btn)
        val firestore = FirebaseFirestore.getInstance()

        mTaskEdit.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
               // TODO("Not yet implemented")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //TODO("Not yet implemented")
                if (p0.toString().equals("")) {
                    mSaveBtn.isEnabled = false
                    mSaveBtn.setBackgroundColor(Color.GRAY)
                } else{
                    mSaveBtn.isEnabled = true
                    mSaveBtn.setBackgroundColor(resources.getColor(R.color.mint))
                }
            }

            override fun afterTextChanged(p0: Editable?) {
             //   TODO("Not yet implemented")
            }


        })
        setDueDate.setOnClickListener {
            val calendar = Calendar.getInstance()

            val MONTH = calendar.get(Calendar.MONTH)
            val YEAR = calendar.get(Calendar.YEAR)
            val DAY = calendar.get(Calendar.DATE)
            val datePickerDialog = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                var month = month + 1
                setDueDate.text = "$dayOfMonth/$month/$year"
                dueDate = "$dayOfMonth/$month/$year"
            }, YEAR, MONTH, DAY)
            datePickerDialog.show()

        }
        mSaveBtn.setOnClickListener {
            val task = mTaskEdit.text.toString()

         //  if (finalIsUpdate) {
               // firestore.collection("task").document(id).update("task", task, "due", dueDate)
                //Toast.makeText(context, "Task Updated", Toast.LENGTH_SHORT).show()
            //} else {
                if (task.isEmpty()) {
                    Toast.makeText(context, "Empty task not Allowed !!", Toast.LENGTH_SHORT).show()
                } else {
                    val taskMap = hashMapOf(
                        "task" to task,
                        "due" to dueDate,
                        "status" to 0,
                        "time" to FieldValue.serverTimestamp()
                    )

                    firestore.collection("task").add(taskMap).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(context, "Task Saved", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, it.exception?.message, Toast.LENGTH_SHORT).show()
                        }
                    }.addOnFailureListener {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            dismiss()
            }

        }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        val activity = activity
        if (activity is OnDialogCloseListner) {
            (activity as OnDialogCloseListner).onDialogClose(dialog)
        }
    }

    }

