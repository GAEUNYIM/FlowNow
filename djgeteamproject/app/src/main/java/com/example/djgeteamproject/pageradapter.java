package com.example.djgeteamproject;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.djgeteamproject.ui.main.SectionsPagerAdapter;
import com.example.djgeteamproject.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;


class PagerRecyclerAdapter RecyclerView.Adapter<PagerViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder =
        PagerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_pager, parent, false))

        override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(bgColors[position], position)
        }

        override fun getItemCount(): Int = bgColors.size
        }

class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

private val textView: TextView = itemView.findViewById(R.id.page_name)

        fun bind(@ColorRes bgColor: Int, position: Int) {
        textView.text = "RecyclerViewAdapter\nPage $position"
        itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, bgColor))
        }
        }