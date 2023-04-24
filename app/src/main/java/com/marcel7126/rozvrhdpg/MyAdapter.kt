package com.marcel7126.rozvrhdpg

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.annotation.LayoutRes

data class Hodina(
    val index: Int,
    val subject: String,
    val teacher: String,
    val classroom: String,
    val note: String
)

data class SearchedItem(
    val text: String,
    val abbr: String,
    val icon: Int
)

class MyAdapter(context: Activity, @LayoutRes private val layoutResource: Int, private val allItems: List<SearchedItem>):
    ArrayAdapter<SearchedItem>(context, layoutResource, allItems),
    Filterable {

    val inflater: LayoutInflater = LayoutInflater.from(context)
    private var mItems: List<SearchedItem> = allItems

    override fun getCount(): Int {
        return mItems.size
    }

    override fun getItem(p0: Int): SearchedItem? {
        return mItems.get(p0)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rview = inflater.inflate(R.layout.item_search, parent, false)
        val tv = rview.findViewById(R.id.tvCardText) as TextView
        tv.setCompoundDrawablesWithIntrinsicBounds(mItems[position].icon, 0, 0, 0)
        tv.text = mItems[position].text
        return rview
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: Filter.FilterResults) {
                mItems = filterResults.values as List<SearchedItem>
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): Filter.FilterResults {
                val queryString = charSequence?.toString()?.lowercase()

                val filterResults = Filter.FilterResults()
                filterResults.values = if (queryString == null || queryString.isEmpty())
                    allItems
                else
                    allItems.filter {
                        it.text.lowercase().contains(queryString) ||
                                it.abbr.lowercase().contains(queryString)
                    }
                return filterResults
            }
        }
    }
}