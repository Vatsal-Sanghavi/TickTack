package com.android.tictac

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    private var totTurnCount = 0
    private var lastCheckedFor = 1
    private var resultDeclared = false
    private val arrayList = ArrayList<Int>()
    private lateinit var adapter: TicTacAdapter
    private var random = Random()

    companion object {
        private var BOX_GRID_NO = 3
        private var TOT_GRIDS = (BOX_GRID_NO * BOX_GRID_NO)
        const val EMPTY = 0
        const val USER = 1
        const val ROBOT = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        btnCheck.setOnClickListener {
            // For development purpose only
            checkResult(lastCheckedFor)
            lastCheckedFor = (if (lastCheckedFor == USER) ROBOT else USER)
        }
        btnReset.setOnClickListener {
            random = Random()
            setupList()
            adapter.notifyDataSetChanged()
        }
        chipGroup.setOnCheckedChangeListener { group, checkedId ->
            BOX_GRID_NO = when (checkedId) {
                R.id.chip1 -> 3
                R.id.chip2 -> 4
                else -> 5
            }
            TOT_GRIDS = (BOX_GRID_NO * BOX_GRID_NO)
            setupRecyclerView()
        }
    }

    private fun setupList() {
        totTurnCount = 0
        resultDeclared = false
        arrayList.clear()
        tvResult.text = ""
        for (item in 0 until TOT_GRIDS) {
            arrayList.add(EMPTY)
        }
    }

    private fun setupRecyclerView() {
        setupList()
        recyclerView.layoutManager = GridLayoutManager(this, BOX_GRID_NO)
        adapter = TicTacAdapter(this, arrayList, object : TicTacAdapter.ClickListener {
            override fun onClick(position: Int) {
                ++totTurnCount
                if (resultDeclared) return
                arrayList[position] = USER
                if ((ceil(totTurnCount / 2f) >= BOX_GRID_NO)) {
                    resultDeclared = checkResult(USER)
                    if (resultDeclared) tvResult.text = getString(R.string.user_win)
                }
                takeRobotTurn()
                if (totTurnCount == TOT_GRIDS && !resultDeclared) {
                    resultDeclared = true
                    tvResult.text = getString(R.string.tie)
                }
                adapter.notifyDataSetChanged()
            }
        })
        recyclerView.adapter = adapter
    }

    private fun checkResult(userType: Int): Boolean {
        lastCheckedFor = userType
//        Toast.makeText(this, "Check result", Toast.LENGTH_LONG).show()
        // Check for diagonal occurrences
        if (diagonalCheck(false, userType)) return true
        if (diagonalCheck(true, userType)) return true
        if (rowColumnCheck(userType)) return true
        return false
    }

    private fun rowColumnCheck(userType: Int): Boolean {
        var matchCount: Int
        for (row in 0 until BOX_GRID_NO) {
            matchCount = 0
            for (column in 0 until BOX_GRID_NO) {
                if (arrayList[((row) * BOX_GRID_NO) + column] == userType) {
                    ++matchCount
                    if (matchCount == BOX_GRID_NO) return true
                }
            }
        }
        for (column in 0 until BOX_GRID_NO) {
            matchCount = 0
            for (row in 0 until BOX_GRID_NO) {
                if (arrayList[((row) * BOX_GRID_NO) + column] == userType) {
                    ++matchCount
                    if (matchCount == BOX_GRID_NO) return true
                }
            }
        }
        return false
    }

    private fun diagonalCheck(isReverse: Boolean, userType: Int): Boolean {
        var matchCount = 0
        if (isReverse) {
            var matchingColumn = BOX_GRID_NO - 1
            for (row in 0 until BOX_GRID_NO) {
                for (column in BOX_GRID_NO - 1 downTo 0) {
                    if (column == matchingColumn) {
                        if (arrayList[((row) * BOX_GRID_NO) + column] == userType) {
                            --matchingColumn
                            ++matchCount
                            if (matchCount == BOX_GRID_NO) return true
                            else break
                        }
                    }
                }
            }
        } else {
            for (row in 0 until BOX_GRID_NO) {
                for (column in 0 until BOX_GRID_NO) {
                    if (row == column) {
                        if (arrayList[((row) * BOX_GRID_NO) + column] == userType) {
                            ++matchCount
                            if (matchCount == BOX_GRID_NO) return true
                        }
                    }
                }
            }
        }
        return false
    }

    private fun takeRobotTurn() {
        if (totTurnCount == TOT_GRIDS || resultDeclared) return
        val digit = random.nextInt(TOT_GRIDS)
        if (arrayList[digit] == EMPTY) {
            ++totTurnCount
            arrayList[digit] = ROBOT
            if ((totTurnCount / 2 >= BOX_GRID_NO)) {
                resultDeclared = checkResult(ROBOT)
                if (resultDeclared) tvResult.text = getString(R.string.robot_win)
            }
        } else {
            takeRobotTurn()
        }
    }

}