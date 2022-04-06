package com.jda00.android.foodmarket

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jda00.android.foodmarket.R
import java.util.*

private const val TAG = "FoodListFragment"

class FoodListFragment : Fragment() {


    /**
     * Required interface for hosting activities
     */
    interface Callbacks {
        fun onFoodSelected(foodId: UUID)
    }

    private var callbacks: Callbacks? = null


    private lateinit var foodRecyclerView: RecyclerView

    // Setting adapter
    private var adapter: FoodAdapter? = FoodAdapter(emptyList())

    private val foodListViewModel: FoodListViewModel by lazy {
        ViewModelProviders.of(this).get(FoodListViewModel::class.java)
    }


    // Override to set and unset the callbacks property.
    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Log.d(TAG, "Total foods: ${foodListViewModel.foods.size}")
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_food_list, container, false)

        foodRecyclerView =
            view.findViewById(R.id.food_recycler_view) as RecyclerView
        foodRecyclerView.layoutManager = LinearLayoutManager(context)


        foodRecyclerView.adapter = adapter


        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        foodListViewModel.foodListLiveData.observe(
            viewLifecycleOwner,
            Observer { foods ->
                foods?.let {
                    Log.i(TAG, "Got foods ${foods.size}")
                    updateUI(foods)
                }
            })
    }

    // Override to set and unset the callbacks property.
    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }


    // Inflate menu resource.
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_food_list, menu)
    }

    // Implement onOptionsItemSelected(MenuItem) to respond to
    // MenuItem selection by creating new Food
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_produce -> {
                val food = Food()
                foodListViewModel.addFood(food)
                callbacks?.onFoodSelected(food.id)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    private fun updateUI(foods: List<Food>) {
        adapter = FoodAdapter(foods)
        foodRecyclerView.adapter = adapter
    }

    // Implement a ViewHolder
    private inner class FoodHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {

        private lateinit var food: Food

        private val pictureImageView: ImageView = itemView.findViewById(R.id.food_image)
        private val titleTextView: TextView = itemView.findViewById(R.id.food_title)
        private val dateTextView: TextView = itemView.findViewById(R.id.freshness_date)


        init {
            itemView.setOnClickListener(this)
        }

        // Bind function
        fun bind(food: Food) {
            this.food = food

            val imageReferenceArray: Array<Int> = arrayOf(
                R.drawable.fruit_and_veg_1200x1200,
                R.drawable.dairy_1200x1200,
                R.drawable.poultry_1200x1200,
                R.drawable.fish_1200x1200,
                R.drawable.meat_1200x1200,
                R.drawable.baked_1200x1200
            )

            pictureImageView.setImageResource(imageReferenceArray[this.food.image])

            titleTextView.text = this.food.name
            dateTextView.text = this.food.date.toString()


        }

        override fun onClick(v: View) {
//            Toast.makeText(context, "${food.name} pressed!", Toast.LENGTH_SHORT)
//                .show()
            callbacks?.onFoodSelected(food.id)
        }

    }


    // Implement adapter to populate RecyclerView
    private inner class FoodAdapter(var foods: List<Food>) : RecyclerView.Adapter<FoodHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                : FoodHolder {
            val view = layoutInflater.inflate(R.layout.list_item_food, parent, false)
            return FoodHolder(view)
        }

        override fun getItemCount() = foods.size

        override fun onBindViewHolder(holder: FoodHolder, position: Int) {
            val food = foods[position]
            holder.bind(food)
        }
    }


    companion object {
        fun newInstance(): FoodListFragment {
            return FoodListFragment()
        }
    }
}

