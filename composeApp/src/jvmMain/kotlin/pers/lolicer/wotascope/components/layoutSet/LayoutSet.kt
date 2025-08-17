package pers.lolicer.wotascope.components.layoutSet

import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintSet

object LayoutSet {
    fun getLayoutSet(number: Int): ConstraintSet?{
        return when(number) {
            0    -> null
            1    -> singleLayout
            2    -> dualLayoutSet
            3    -> tripleLayoutSet
            4    -> quadLayoutSet
            5    -> pentaLayout
            6    -> hexaLayoutSet
            7    -> heptaLayoutSet
            8    -> octaLayoutSet
            9    -> nonaLayoutSet
            else -> throw IllegalArgumentException("布局数错误。")
        }
    }

    private val singleLayout = ConstraintSet{
        val panel1 = createRefFor("panel1")
        constrain(panel1){
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
        }
    }

    private val dualLayoutSet = ConstraintSet{
        val (panel1, panel2) = createRefsFor("panel1", "panel2")
        val guideline = createGuidelineFromTop(1 / 4f)

        constrain(panel1) {
            start.linkTo(parent.start)
            end.linkTo(panel2.start)
            top.linkTo(guideline)
            // bottom.linkTo(parent.bottom)
        }
        constrain(panel2) {
            start.linkTo(panel1.end)
            end.linkTo(parent.end)
            top.linkTo(panel1.top)
            bottom.linkTo(panel1.bottom)
        }
    }

    private val tripleLayoutSet = ConstraintSet{
        val (panel1, panel2, panel3) = createRefsFor("panel1", "panel2", "panel3")

        constrain(panel1){
            start.linkTo(parent.start)
            end.linkTo(panel2.start)
            top.linkTo(parent.top)
            bottom.linkTo(panel3.top)
        }
        constrain(panel2){
            start.linkTo(panel1.end)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
            bottom.linkTo(panel3.top)
        }
        constrain(panel3){
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(panel1.bottom)
            bottom.linkTo(parent.bottom)
        }
    }

    private val quadLayoutSet = ConstraintSet{
        val (panel1, panel2, panel3, panel4) = createRefsFor("panel1", "panel2", "panel3", "panel4")

        constrain(panel1){
            start.linkTo(parent.start)
            end.linkTo(panel2.start)
            top.linkTo(parent.top)
            bottom.linkTo(panel3.top)
        }
        constrain(panel2){
            start.linkTo(panel1.end)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
            bottom.linkTo(panel4.top)
        }
        constrain(panel3){
            start.linkTo(parent.start)
            end.linkTo(panel4.start)
            top.linkTo(panel1.bottom)
            bottom.linkTo(parent.bottom)
        }
        constrain(panel4){
            start.linkTo(panel3.end)
            end.linkTo(parent.end)
            top.linkTo(panel2.bottom)
            bottom.linkTo(parent.bottom)
        }
    }

    private val pentaLayout = ConstraintSet{
        val guideline = createGuidelineFromTop(1/6f)
        val (panel1, panel2, panel3, panel4, panel5) = createRefsFor("panel1", "panel2", "panel3", "panel4", "panel5")

        createHorizontalChain(panel1, panel2, panel3, chainStyle = ChainStyle.Spread)
        createHorizontalChain(panel4, panel5, chainStyle = ChainStyle.Packed)

        constrain(panel1){
            top.linkTo(guideline)
            bottom.linkTo(panel4.top)
        }
        constrain(panel2){
            top.linkTo(panel1.top)
            bottom.linkTo(panel1.bottom)
        }
        constrain(panel3){
            top.linkTo(panel1.top)
            bottom.linkTo(panel1.bottom)
        }
        constrain(panel4){
            top.linkTo(panel1.bottom)
            bottom.linkTo(parent.bottom)
        }
        constrain(panel5){
            top.linkTo(panel4.top)
            bottom.linkTo(panel4.bottom)
        }
    }

    private val hexaLayoutSet = ConstraintSet{
        val guideline = createGuidelineFromTop(1/6f)
        val (panel1, panel2, panel3, panel4, panel5, panel6) = createRefsFor("panel1", "panel2", "panel3", "panel4", "panel5", "panel6")

        createHorizontalChain(panel1, panel2, panel3, chainStyle = ChainStyle.Spread)
        createHorizontalChain(panel4, panel5, panel6, chainStyle = ChainStyle.Spread)

        constrain(panel1){
            top.linkTo(guideline)
            bottom.linkTo(panel4.top)
        }
        constrain(panel2){
            top.linkTo(panel1.top)
            bottom.linkTo(panel1.bottom)
        }
        constrain(panel3){
            top.linkTo(panel1.top)
            bottom.linkTo(panel1.bottom)
        }
        constrain(panel4){
            top.linkTo(panel1.bottom)
            bottom.linkTo(parent.bottom)
        }
        constrain(panel5){
            top.linkTo(panel4.top)
            bottom.linkTo(panel4.bottom)
        }
        constrain(panel6){
            top.linkTo(panel4.top)
            bottom.linkTo(panel4.bottom)
        }
    }

    private val heptaLayoutSet = ConstraintSet{
        val (panel1, panel2, panel3, panel4, panel5, panel6, panel7) = createRefsFor("panel1", "panel2", "panel3", "panel4", "panel5", "panel6", "panel7")

        createHorizontalChain(panel1, panel2, chainStyle = ChainStyle.Packed)
        createHorizontalChain(panel3, panel4, panel5, chainStyle = ChainStyle.Spread)
        createHorizontalChain(panel6, panel7, chainStyle = ChainStyle.Packed)

        constrain(panel1){
            top.linkTo(parent.top)
            bottom.linkTo(panel3.top)
        }
        constrain(panel2){
            top.linkTo(panel1.top)
            bottom.linkTo(panel1.bottom)
        }
        constrain(panel3){
            top.linkTo(panel1.bottom)
            bottom.linkTo(panel6.top)
        }
        constrain(panel4){
            top.linkTo(panel3.top)
            bottom.linkTo(panel3.bottom)
        }
        constrain(panel5){
            top.linkTo(panel3.top)
            bottom.linkTo(panel3.bottom)
        }
        constrain(panel6){
            top.linkTo(panel3.bottom)
            bottom.linkTo(parent.bottom)
        }
        constrain(panel7){
            top.linkTo(panel6.top)
            bottom.linkTo(panel6.bottom)
        }
    }

    private val octaLayoutSet = ConstraintSet{
        val (panel1, panel2, panel3, panel4, panel5, panel6, panel7, panel8) = createRefsFor("panel1", "panel2", "panel3", "panel4", "panel5", "panel6", "panel7", "panel8")

        createHorizontalChain(panel1, panel2, panel3, chainStyle = ChainStyle.Spread)
        createHorizontalChain(panel4, panel5, panel6, chainStyle = ChainStyle.Spread)
        createHorizontalChain(panel7, panel8, chainStyle = ChainStyle.Packed)

        constrain(panel1){
            top.linkTo(parent.top)
            bottom.linkTo(panel4.top)
        }
        constrain(panel2){
            top.linkTo(panel1.top)
            bottom.linkTo(panel1.bottom)
        }
        constrain(panel3){
            top.linkTo(panel1.top)
            bottom.linkTo(panel1.bottom)
        }
        constrain(panel4){
            top.linkTo(panel1.bottom)
            bottom.linkTo(panel7.top)
        }
        constrain(panel5){
            top.linkTo(panel4.top)
            bottom.linkTo(panel4.bottom)
        }
        constrain(panel6){
            top.linkTo(panel4.top)
            bottom.linkTo(panel4.bottom)
        }
        constrain(panel7){
            top.linkTo(panel4.bottom)
            bottom.linkTo(parent.bottom)
        }
        constrain(panel8){
            top.linkTo(panel7.top)
            bottom.linkTo(panel7.bottom)
        }
    }

    private val nonaLayoutSet = ConstraintSet{
        val (panel1, panel2, panel3, panel4, panel5, panel6, panel7, panel8, panel9) = createRefsFor("panel1", "panel2", "panel3", "panel4", "panel5", "panel6", "panel7", "panel8", "panel9")

        createHorizontalChain(panel1, panel2, panel3, chainStyle = ChainStyle.Spread)
        createHorizontalChain(panel4, panel5, panel6, chainStyle = ChainStyle.Spread)
        createHorizontalChain(panel7, panel8, panel9, chainStyle = ChainStyle.Spread)

        constrain(panel1){
            top.linkTo(parent.top)
            bottom.linkTo(panel4.top)
        }
        constrain(panel2){
            top.linkTo(panel1.top)
            bottom.linkTo(panel1.bottom)
        }
        constrain(panel3){
            top.linkTo(panel1.top)
            bottom.linkTo(panel1.bottom)
        }
        constrain(panel4){
            top.linkTo(panel2.bottom)
            bottom.linkTo(panel7.top)
        }
        constrain(panel5){
            top.linkTo(panel4.top)
            bottom.linkTo(panel4.bottom)
        }
        constrain(panel6){
            top.linkTo(panel4.top)
            bottom.linkTo(panel4.bottom)
        }
        constrain(panel7){
            top.linkTo(panel4.bottom)
            bottom.linkTo(parent.bottom)
        }
        constrain(panel8){
            top.linkTo(panel7.top)
            bottom.linkTo(panel7.bottom)
        }
        constrain(panel9){
            top.linkTo(panel7.top)
            bottom.linkTo(panel7.bottom)
        }
    }
}