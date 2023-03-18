package com.example.not_last_dish.data

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.not_last_dish.advenced_httpurlconnection.PutData
import com.example.not_last_dish.data.model.LoggedInUser
import java.io.IOException


/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Result<LoggedInUser> {
        return try {
            // TODO: handle loggedInUser authentication
            // connection to MySQL DataBase with php scripts in local server XAMPP
            var user = LoggedInUser("1", "")
            val handler = Handler(Looper.getMainLooper())
            handler.post {
                //Starting Write and Read data with URL
                //Creating array for parameters
                val field = arrayOfNulls<String>(2)
                field[0] = "name"
                field[1] = "password"
                //Creating array for data
                val data = arrayOfNulls<String>(2)
                data[0] = username
                data[1] = password
/*
                Example of using class PutData:

                need to change ip for connect from mobile to computer where set MySQL DataBase
                else it will try to connect to yourself (mobile connect to his own ip)
                in OS Windows use combinations 'Windows' + 'R' type 'cmd' and press 'Enter'
                type 'ipconfig' in terminal
                copy Ipv4 from 'Адаптер беспроводной локальной сети Беспроводная сеть'
                and paste into ip field after http://<ip:port>/<directory path to php script>

                Scripts need to paste into local server program directory
                Examples: xampp/htdocs/<php scripts or directory with php scripts>
                Addition info "how does it work?" is here: https://www.youtube.com/watch?v=X8oD4q3XtQQ
                192.168.56.1
                192.168.0.14
                http://192.168.0.14:80/LoginRegister/test.php
 */
                val putData = PutData(
                    "http://192.168.0.14:80/Config/index.php",
                    "POST",
                    field,
                    data
                )
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        val result: String = putData.getResult()
                        //End ProgressBar (Set visibility to GONE)
                        when (result) {
                            "Login Success" -> {
                                user = LoggedInUser("1", data[0]!!)
                                Log.v("TAG121", "$result $username $password")
                            }
                            "Username or Password wrong" -> {
                                Log.v("TAG121", "$result $username $password")
                            } else -> {
                    //                            throw Exception()
                                Log.v("TAG121", "$result $username $password")
                            }
                        }
                    }
                }
                //End Write and Read data with URL
            }

//            val user = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe")
            Result.Success(user)
        } catch (e: Throwable) {
            Result.Error(IOException("Error logging in", e))
        }
////        Structure rule one input - one exit
//        val result: Result<LoggedInUser> = try {
//            // TODO: handle loggedInUser authentication
////            connection to MySQL DataBase with php scripts in local server XAMPP
//            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe")
//            Result.Success(fakeUser)
//        } catch (e: Throwable) {
//            Result.Error(IOException("Error logging in", e))
//        }
//        return result
    }

    fun logout() {
        // TODO: revoke authentication
    }
}