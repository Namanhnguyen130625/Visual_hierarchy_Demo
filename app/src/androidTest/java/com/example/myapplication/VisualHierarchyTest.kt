package com.example.myapplication

import android.graphics.Bitmap
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.Espresso.pressBack
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

@RunWith(AndroidJUnit4::class)
class VisualHierarchyTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()

    private fun openLesson(index: Int) {
        rule.onNodeWithTag("home").performScrollToNode(hasTestTag("lesson_$index"))
        rule.onNodeWithTag("lesson_$index").performClick()
    }

    private fun screenshot(name: String) {
        rule.waitForIdle()
        // Đợi khung hình được hiển thị xong trước khi chụp từ SurfaceFlinger.
        android.os.SystemClock.sleep(250)
        val bitmap = InstrumentationRegistry.getInstrumentation().uiAutomation.takeScreenshot()
        val directory = File(rule.activity.getExternalFilesDir(null), "screenshots").apply { mkdirs() }
        File(directory, "$name.png").outputStream().use { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) }
        bitmap.recycle()
    }

    @Test fun allLessonsShowTheBookExamples() {
        screenshot("00_home")
        for (index in 0..4) {
            openLesson(index)
            rule.onNodeWithText("Visual hierarchy").assertIsDisplayed()
            if (index == 1) {
                rule.onNodeWithTag("mode_after").assertDoesNotExist()
                rule.onNodeWithTag("example_images").assertIsSelected()
                rule.onNodeWithTag("cart_LargeQuantity").assertDoesNotExist()
                rule.onNodeWithTag("cart_LargeImages").performScrollTo()
                screenshot("02_large_images")
                rule.onNodeWithTag("remove_Avocado").performScrollTo().performClick()
                rule.onNodeWithTag("example_quantity").performClick().assertIsSelected()
                rule.onNodeWithTag("cart_LargeImages").assertDoesNotExist()
                rule.onNodeWithTag("cart_total").assertTextEquals("$11.20")
                rule.onNodeWithTag("cart_LargeQuantity").performScrollTo()
                screenshot("02_large_quantity")
                rule.activityRule.scenario.recreate()
                rule.onNodeWithTag("example_quantity").assertIsSelected()
                rule.onNodeWithTag("example_images").performClick().assertIsSelected()
                rule.onNodeWithTag("cart_total").assertTextEquals("$6.70")
            } else {
                val sample = when (index) {
                    0 -> "cart_Equal"
                    2 -> "metrics"
                    3 -> "law_card"
                    else -> "property_card"
                }
                rule.onNodeWithTag(sample).performScrollTo()
                screenshot("0${index + 1}_before")
                if (index == 0) {
                    rule.onNodeWithTag("colors_only").performScrollTo().performClick()
                    rule.onNodeWithTag("cart_ColorsOnly").performScrollTo()
                    screenshot("01_colors_only")
                }
                rule.onNodeWithTag("mode_after").performClick().assertIsSelected()
                val optimizedSample = if (index == 0) "cart_Optimized" else sample
                rule.onNodeWithTag(optimizedSample).performScrollTo()
                screenshot("0${index + 1}_after")
                if (index == 2) {
                    rule.onNodeWithTag("metrics").performScrollToNode(hasTestTag("metric_Views"))
                    rule.onNodeWithTag("metric_Views").assertTextEquals("2,121")
                }
                if (index == 4) rule.onNodeWithTag("property_price").assertTextEquals("£3,273,279")
            }
            rule.onNodeWithTag("back").performClick()
            rule.onNodeWithTag("home").assertExists()
        }
    }

    @Test fun removingProductsUpdatesTheTotalAndResetRestoresThem() {
        openLesson(0)
        rule.onNodeWithTag("cart_total").assertTextEquals("$11.20")
        rule.onNodeWithTag("remove_Avocado").performScrollTo().performClick()
        rule.onNodeWithTag("cart_total").assertTextEquals("$6.70")
        rule.onNodeWithTag("mode_after").performClick()
        rule.onNodeWithTag("cart_total").assertTextEquals("$6.70")
        rule.onNodeWithTag("remove_Lime").performScrollTo().performClick()
        rule.onNodeWithTag("cart_total").assertTextEquals("$2.50")
        rule.onNodeWithTag("remove_Orange").performScrollTo().performClick()
        rule.onNodeWithTag("cart_total").assertTextEquals("$0.00")
        rule.onNodeWithText("Your cart is empty.").assertExists()
        rule.onNodeWithTag("checkout").assertIsNotEnabled()
        rule.onNodeWithTag("reset_cart").performScrollTo().performClick()
        rule.onNodeWithTag("cart_total").assertTextEquals("$11.20")
        rule.onNodeWithTag("checkout").assertIsEnabled()
    }

    @Test fun checkoutDisplaysTheCurrentTotal() {
        openLesson(0)
        rule.onNodeWithTag("remove_Lime").performScrollTo().performClick()
        rule.onNodeWithTag("checkout").performScrollTo().performClick()
        rule.onNodeWithText("Thanh toán").assertIsDisplayed()
        rule.onNodeWithText("Tổng tiền: $7.00", substring = true).assertIsDisplayed()
        rule.onNodeWithText("Đóng").performClick()
        rule.onNodeWithText("Thanh toán").assertDoesNotExist()
    }

    @Test fun activityRecreationKeepsModeAndCartState() {
        openLesson(0)
        rule.onNodeWithTag("remove_Avocado").performScrollTo().performClick()
        rule.onNodeWithTag("mode_after").performClick()
        rule.activityRule.scenario.recreate()
        rule.onNodeWithTag("mode_after").assertIsSelected()
        rule.onNodeWithTag("cart_total").assertTextEquals("$6.70")
        rule.onNodeWithTag("remove_Avocado").assertDoesNotExist()
    }

    @Test fun androidBackReturnsToTheList() {
        openLesson(3)
        pressBack()
        rule.onNodeWithTag("home").assertIsDisplayed()
        rule.onNodeWithTag("mode_after").assertDoesNotExist()
    }
}
