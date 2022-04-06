package com.jda00.android.foodmarket

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import java.util.*
import androidx.lifecycle.Observer



private const val TAG = "FoodFragment"
private const val ARG_FOOD_ID = "food_id"

class FoodFragment : Fragment(), View.OnClickListener {

    private lateinit var food: Food

    private lateinit var nameField: EditText
    private lateinit var quantityField: EditText
    private lateinit var freshnessField: EditText

    private lateinit var addProduceButton: Button

    private lateinit var spinner: Spinner

    private val foodDetailViewModel: FoodDetailViewModel by lazy {
        ViewModelProviders.of(this).get(FoodDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        food = Food()

        // Retrieve food ID from arguments
        val foodId: UUID = arguments?.getSerializable(ARG_FOOD_ID) as UUID
//        Log.d(TAG, "args bundle food ID: $foodId")

        // Hook up FoodFragment up to FoodDetailViewModel
        foodDetailViewModel.loadFood(foodId)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_food, container, false)


        nameField = view.findViewById(R.id.food_name)
        quantityField = view.findViewById(R.id.food_quantity)
        freshnessField = view.findViewById(R.id.food_freshness)

        addProduceButton = view.findViewById(R.id.food_date)
        addProduceButton.setOnClickListener(this)

        // Button
        addProduceButton.apply {
            text = food.date.toString()
            isEnabled = true

        }

        spinner = view.findViewById(R.id.category)

        return view
    }


    // Observe FoodDetailViewModel’s foodLiveData and update the UI any time new data is published.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        foodDetailViewModel.foodLiveData.observe(
            viewLifecycleOwner,
            Observer { food ->
                food?.let {
                    this.food = food
                    updateUI()
                }
            })
    }


    override fun onStart() {
        super.onStart()

        spinner.adapter = ArrayAdapter.createFromResource(
            requireActivity().applicationContext,
            R.array.food_categories,
            android.R.layout.simple_spinner_item
        )

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                food.category = position
                food.image = position
            }
        }

        // Implement TextWatcher interface
        val titleWatcher1 = object : TextWatcher {

            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                food.name = sequence.toString()
            }

            override fun afterTextChanged(sequence: Editable?) {
            }
        }

        val titleWatcher2 = object : TextWatcher {

            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                food.quantity = sequence.toString()
            }

            override fun afterTextChanged(sequence: Editable?) {
            }
        }

        val titleWatcher3 = object : TextWatcher {

            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                food.freshFor = sequence.toString()
            }


            override fun afterTextChanged(sequence: Editable?) {
            }
        }


        nameField.addTextChangedListener(titleWatcher1)
        quantityField.addTextChangedListener(titleWatcher2)
        freshnessField.addTextChangedListener(titleWatcher3)

    }


//    // Inflate menu resource.
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater.inflate(R.menu.fragment_food_detail, menu)
//    }
//
//    // Implement onOptionsItemSelected(MenuItem) to respond to
//    // MenuItem selection by deleting selected Food item
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.delete_produce -> {
//                foodDetailViewModel.deleteFood(this.food)
//                activity?.onBackPressed()
//                true
//
//            }
//            else -> return super.onOptionsItemSelected(item)
//        }
//    }


    // Saving onStop()
    override fun onStop() {
        super.onStop()
        foodDetailViewModel.saveFood(food)
        Log.d(TAG, food.toString())
    }


    private fun updateUI() {
        nameField.setText(food.name)
        quantityField.setText(food.quantity)
        freshnessField.setText(food.freshFor)
        spinner.setSelection(food.category)
    }

    companion object {

        fun newInstance(foodId: UUID): FoodFragment {
            val args = Bundle().apply {
                putSerializable(ARG_FOOD_ID, foodId)
            }
            return FoodFragment().apply {
                arguments = args
            }
        }
    }


    override fun onClick(v: View?) {
        activity?.onBackPressed()
    }


}
