package com.apptrovertscube.manojprabahar.myapplication

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.*
import android.widget.*
import com.parse.*
import com.special.ResideMenu.ResideMenu
import com.special.ResideMenu.ResideMenuItem
import io.realm.Realm
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private var resideMenu: ResideMenu? = null
    private var itemHome: ResideMenuItem? = null
    private var itemProfile: ResideMenuItem? = null
    private var itemhelp: ResideMenuItem? = null
    private var itemSettings: ResideMenuItem? = null

    private var itemRightHome: ResideMenuItem? = null
    private var futureOrdersRight: ResideMenuItem? = null
    private var thismonthordersright: ResideMenuItem? = null
    private var pastordersright: ResideMenuItem? = null
    private var popupwdw: PopupWindow? = null
    private var root: CoordinatorLayout? = null


    internal var a : Boolean = false
    var a1 : Int = 0



    val lastthreemonth = mutableListOf<Int>()
    val numbers: Array<String> = arrayOf("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct", "Nov","Dec")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var calendar = Calendar.getInstance()
        val formatter = SimpleDateFormat("ddMMyyyy")
        calendar.setTime(Date())
        calendar.add(Calendar.DATE, 1)
        val finaldate = formatter.format(calendar.getTime())
        a1 = Integer.parseInt(finaldate)

        val realm = Realm.getDefaultInstance();
        realm.close();
        root = findViewById(R.id.mainroot) as CoordinatorLayout

        //val toolbar = findViewById(R.id.toolbar) as Toolbar
        //setSupportActionBar(toolbar)
        setUpMenu()
        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            //val intent = Intent(this, LoginActivity::class.java)
//            val today = Date()
//            val con = Calendar.getInstance()
//            //con.setTime(today)
//            con.add(Calendar.DATE, 1)
//            val myFormat = SimpleDateFormat("ddMMyyyy")
//            val reformattedStr = myFormat.format(con)
            val t = Date()
            val c = Calendar.getInstance()
//            con.add(Calendar.DATE,1)

            val format1 = SimpleDateFormat("ddMMyyyy")
            val reformat = format1.format(t)
            Log.d("The datevalis", "${Calendar.getInstance().get(Calendar.MONTH)}   ${reformat}")
            //intent.putExtra("dateval", reformattedStr)
            //startActivity(intent)
            //finish()
        }

        val availvredit = findViewById(R.id.availcredit) as Button
        val query = ParseQuery.getQuery<ParseObject>("DefaultItem")
        query.findInBackground { objects, e ->
            if (e == null) {
                if (objects.size > 0) {
                    var cre = objects[0].getDouble("credit")
                    Log.d("yyyyyyyuuuu","${cre}<-----");
                    val df = DecimalFormat("#.00")
                    if((LoginActivity.treshold - cre) > 0)
                    availvredit.text="Available Credit : ${df.format(LoginActivity.treshold - cre)}"
                    else  availvredit.text="Available Credit : 0"
                }
            }
        }


        val sft = findViewById(R.id.shopfortmrw) as Button
        sft.setOnClickListener { view ->
            if(LoginActivity.check==true) {
                if(LoginActivity.treshold > 0) {
                    if (LoginActivity.credit > LoginActivity.treshold) {
                        val query = ParseQuery.getQuery<ParseObject>("SelectedItems")
                        query.whereEqualTo("id", a)
                        query.findInBackground { objects, e ->
                            if (e == null) {
                                if (objects.size > 0) {
                                    val intent = Intent(this, DemoActivity::class.java)
                                    val extras = Bundle()
                                    extras.putString("dateval", "tomo")
                                    extras.putString("category", "all")
                                    extras.putString("demo", "false")
                                    intent.putExtras(extras)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(applicationContext, "Please complete the payment before proceeding as the credit limit is hit.", Toast.LENGTH_LONG).show()
                                }
                            } else {
                                Toast.makeText(applicationContext, "Please check your internet or try again after sometime..", Toast.LENGTH_LONG).show();
                            }
                        }

                    } else {
                            val intent = Intent(this, DemoActivity::class.java)
                            val extras = Bundle()
                            extras.putString("dateval", "tomo")
                            extras.putString("category", "all")
                            extras.putString("demo", "false")
                            intent.putExtras(extras)
                            startActivity(intent)
                            finish()

                    }
                }
                else
                {
                    Toast.makeText(applicationContext, "Please check your internet or try again after sometime..", Toast.LENGTH_LONG).show()
                }
            }
            else
            {
                Toast.makeText(applicationContext,"Please add a address before proceeding  or confirm internet connectivity and try in few seconds", Toast.LENGTH_LONG).show()

            }
        }

        val sff = findViewById(R.id.shopforftr) as Button
        sff.setOnClickListener { view ->
            if(LoginActivity.check) {
                val intent = Intent(this, CalendarActivity::class.java)
                startActivity(intent)
            }else
            {
                Toast.makeText(applicationContext,"Please add a address before proceeding  or confirm internet connectivity and try in few seconds", Toast.LENGTH_LONG).show()

            }
        }

    }

    private val menuListener = object : ResideMenu.OnMenuListener {
        override fun openMenu() {
            //Toast.makeText(MainActivity.this, "Menu is opened!", Toast.LENGTH_SHORT).show();
        }

        override fun closeMenu() {
            //Toast.makeText(MainActivity.this, "Menu is closed!", Toast.LENGTH_SHORT).show();
        }
    }

    private fun setUpMenu() {

        // attach to current activity;
        resideMenu = ResideMenu(this)

        resideMenu?.setUse3D(true)
        resideMenu?.setBackground(R.drawable.gradient)
        resideMenu?.attachToActivity(this)
        resideMenu?.setMenuListener(menuListener)
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
        resideMenu?.setScaleValue(0.6f)

        // create menu items;
        itemHome = ResideMenuItem(this, R.drawable.ic_homecloudsane, "Holder")
        itemProfile = ResideMenuItem(this, R.drawable.ic_cloud_cloudsane, "Holder2")
        itemhelp = ResideMenuItem(this, R.drawable.ic_network, "Help")
        itemSettings = ResideMenuItem(this, R.drawable.ic_helpcloudsane, "Address")

        itemRightHome = ResideMenuItem(this, R.drawable.ic_homecloudsane, "Pay Now")
        futureOrdersRight = ResideMenuItem(this, R.drawable.ic_cloud_cloudsane, "Future Orders")
        thismonthordersright = ResideMenuItem(this, R.drawable.ic_network, "This Month")
        pastordersright = ResideMenuItem(this, R.drawable.ic_helpcloudsane, "Past Orders")

        futureOrdersRight?.setOnClickListener { view ->
            val intent = Intent(this, MonthView::class.java)
            intent.putExtra("whatmonth","futuremonths");
            startActivity(intent)
            finish()
        }



                thismonthordersright?.setOnClickListener { view ->

            val intent = Intent(this, MonthView::class.java)
            intent.putExtra("whatmonth","thismonth")
            startActivity(intent)
            finish()
        }

        pastordersright?.setOnClickListener(View.OnClickListener { view ->

            var mon: Int = Calendar.getInstance().get(Calendar.MONTH)
            var year: Int = Calendar.getInstance().get(Calendar.YEAR)
            //var mon: Int = 0
            Log.d("lololo","${mon}<>${year}")

            root?.alpha = 0f
            //resideMenu?.alpha = 0F
            resideMenu?.closeMenu();
            lastthreemonth.clear()
            if(mon > 2) {

                //TODO PASS STRINGS in this method for setTag with year
                setupmonths(mon-3,mon-2,mon-1,year,"${(mon-2)}${year}","${(mon-1)}${year}","${(mon)}${year}");
//                lastthreemonth?.add(mon-3)
//                lastthreemonth?.add(mon-2)
//                lastthreemonth?.add(mon-1)
//                val layoutInflater = LayoutInflater.from(this)
//                val popupview = layoutInflater.inflate(R.layout.monthpopup, null)
//                val ll = popupview.findViewById<View>(R.id.monthroot) as LinearLayout
//                val fm = popupview.findViewById<View>(R.id.firstmonth) as CustomTextView
//                fm.setStrokeWidth(1);
//                fm.setStrokeColor("#FFFFFF");
//                fm.setSolidColor("#FFFFFF");
//
//                fm.setText(numbers[mon-3])
//                fm.setOnClickListener { view ->
//                    val intent = Intent(this, MonthView::class.java)
//                    startActivity(intent)
//                    finish()
//
//                }
//                val sm = popupview.findViewById<View>(R.id.secondmonth) as CustomTextView
//                sm.setStrokeWidth(1);
//                sm.setStrokeColor("#FFFFFF");
//                sm.setSolidColor("#FFFFFF");
//                sm.setText(numbers[mon-2])
//                val tm = popupview.findViewById<View>(R.id.thirdmonth) as CustomTextView
//                tm.setStrokeWidth(1);
//                tm.setStrokeColor("#FFFFFF");
//                tm.setText(numbers[mon-1])
//                tm.setSolidColor("#FFFFFF");
//                Toast.makeText(applicationContext,"The value is ${lastthreemonth?.toString()}", Toast.LENGTH_LONG).show()
//                popupwdw = PopupWindow(popupview, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
//                popupwdw?.setAnimationStyle(android.R.style.Animation_Dialog)
//                popupwdw?.setOutsideTouchable(true)
//                popupwdw?.setOnDismissListener(PopupWindow.OnDismissListener {
//                    root?.alpha = 255f
//                    //resideMenu?.alpha = 255f
//                    resideMenu?.openMenu(ResideMenu.DIRECTION_LEFT);
//
//                })
//                popupwdw?.showAtLocation(resideMenu, Gravity.CENTER, 0, 0)
            }
            else if(mon == 2)
            {
                setupmonths(11,0,1,year,"12+${year-1}","1${year}","2${year}");
            }else if(mon==1)
            {
                setupmonths(10,11,0,year,"11${year-1}","12${year-1}","1${year}");
            }else if(mon == 0)
            {
                setupmonths(9,10,11,year,"10${year-1}","11${year-1}","12${year-1}");
            }

            //Toast.makeText(applicationContext, "The setting is Profile", Toast.LENGTH_SHORT).show()
        })
        //itemCalendar?.setOnClickListener(View.OnClickListener { Toast.makeText(applicationContext, "The setting is Calendar", Toast.LENGTH_SHORT).show() })
        itemSettings?.setOnClickListener(View.OnClickListener {
            view ->
            val intent = Intent(this, AddressForm::class.java)
            intent.putExtra("whatmonth","thismonth")
            startActivity(intent)
            finish()
        })

        itemhelp?.setOnClickListener(View.OnClickListener {
            view ->
            val intent = Intent(this, HelpActivity::class.java)
            startActivity(intent)
            finish()
        })

        itemRightHome?.setOnClickListener(View.OnClickListener { view ->

            val intent = Intent(this, Payment::class.java)
            startActivity(intent)
            finish()

            //Toast.makeText(applicationContext, "The setting is Right Home", Toast.LENGTH_SHORT).show()
        })
        //futureOrdersRight?.setOnClickListener(View.OnClickListener { Toast.makeText(applicationContext, "The setting is Right Profile", Toast.LENGTH_SHORT).show() })
        //thismonthordersright?.setOnClickListener(View.OnClickListener { Toast.makeText(applicationContext, "The setting is Right Calendar", Toast.LENGTH_SHORT).show() })
        //pastordersright?.setOnClickListener(View.OnClickListener { Toast.makeText(applicationContext, "The setting is Right Settings", Toast.LENGTH_SHORT).show() })


        resideMenu?.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT)
        resideMenu?.addMenuItem(itemProfile, ResideMenu.DIRECTION_LEFT)
        resideMenu?.addMenuItem(itemhelp, ResideMenu.DIRECTION_LEFT)
        resideMenu?.addMenuItem(itemSettings, ResideMenu.DIRECTION_LEFT)

        resideMenu?.addMenuItem(itemRightHome, ResideMenu.DIRECTION_RIGHT)
        resideMenu?.addMenuItem(futureOrdersRight, ResideMenu.DIRECTION_RIGHT)
        resideMenu?.addMenuItem(thismonthordersright, ResideMenu.DIRECTION_RIGHT)
        resideMenu?.addMenuItem(pastordersright, ResideMenu.DIRECTION_RIGHT)
        // You can disable a direction by setting ->
        // resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        findViewById(R.id.ham).setOnClickListener { resideMenu?.openMenu(ResideMenu.DIRECTION_LEFT) }
        findViewById(R.id.menuright).setOnClickListener { resideMenu?.openMenu(ResideMenu.DIRECTION_RIGHT) }


    }

    fun setupmonths(third: Int, second: Int, first: Int, year: Int, sthird: String, ssecond: String, sfirst: String)
    {
        lastthreemonth?.add(third)
        lastthreemonth?.add(second)
        lastthreemonth?.add(first)
        val layoutInflater = LayoutInflater.from(this)
        val popupview = layoutInflater.inflate(R.layout.monthpopup, null)
        val ll = popupview.findViewById<View>(R.id.monthroot) as LinearLayout
        val fm = popupview.findViewById<View>(R.id.firstmonth) as CustomTextView
        fm.setStrokeWidth(1);
        fm.setStrokeColor("#FFFFFF");
        fm.setSolidColor("#FFFFFF");
        fm.setTag(sthird)
        //52017 -> substr(3,5) 122017 -> substr(4,6)
        fm.setText("${numbers[third]}-${sthird.substring(sthird.length-2, sthird.length)}")
        fm.setOnClickListener { view ->
            val intent = Intent(this, MonthView::class.java)
            intent.putExtra("whatmonth","pastmonth");
            intent.putExtra("mmyy", "${view.getTag()}")
            startActivity(intent)
            finish()
            //Toast.makeText(applicationContext,"The value is ${view.getTag()}", Toast.LENGTH_LONG).show()
        }


        val sm = popupview.findViewById<View>(R.id.secondmonth) as CustomTextView
        sm.setStrokeWidth(1);
        sm.setStrokeColor("#FFFFFF");
        sm.setSolidColor("#FFFFFF");
        sm.setTag(ssecond)
        sm.setText("${numbers[second]}-${ssecond.substring(ssecond.length-2, ssecond.length)}")
        sm.setOnClickListener { view ->
            val intent = Intent(this, MonthView::class.java)
            intent.putExtra("whatmonth","pastmonth");
            intent.putExtra("mmyy", "${view.getTag()}")
            startActivity(intent)
            finish()
            //Toast.makeText(applicationContext,"The value is ${view.getTag()}", Toast.LENGTH_LONG).show()

        }


        val tm = popupview.findViewById<View>(R.id.thirdmonth) as CustomTextView
        tm.setStrokeWidth(1);
        tm.setStrokeColor("#FFFFFF");
        tm.setTag(sfirst)
        tm.setText("${numbers[first]}-${sfirst.substring(sfirst.length-2, sfirst.length)}")
        tm.setSolidColor("#FFFFFF");
        tm.setOnClickListener { view ->
            val intent = Intent(this, MonthView::class.java)
            intent.putExtra("whatmonth","pastmonth");
            intent.putExtra("mmyy", "${view.getTag()}")
            startActivity(intent)
            finish()
            //Toast.makeText(applicationContext,"The value is ${view.getTag()}", Toast.LENGTH_LONG).show()

        }
        //Toast.makeText(applicationContext,"The value is ${lastthreemonth?.toString()}", Toast.LENGTH_LONG).show()
        popupwdw = PopupWindow(popupview, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        popupwdw?.setAnimationStyle(android.R.style.Animation_Dialog)
        popupwdw?.setOutsideTouchable(true)
        popupwdw?.setOnDismissListener(PopupWindow.OnDismissListener {
            root?.alpha = 255f
            //resideMenu?.alpha = 255f
            resideMenu?.openMenu(ResideMenu.DIRECTION_LEFT);

        })
        popupwdw?.showAtLocation(resideMenu, Gravity.CENTER, 0, 0)
    }


    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        return resideMenu!!.dispatchTouchEvent(ev)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    public fun round(value: Double, places: Int): Double {
        if (places < 0) throw IllegalArgumentException()

        var bd = BigDecimal(value)
        bd = bd.setScale(places, RoundingMode.HALF_UP)
        return bd.toDouble()
    }
}
