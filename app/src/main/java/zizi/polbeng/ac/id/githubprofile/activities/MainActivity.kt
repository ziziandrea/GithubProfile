package zizi.polbeng.ac.id.githubprofile.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.request.RequestOptions
import retrofit2.Call
import zizi.polbeng.ac.id.githubprofile.GlideApp
import zizi.polbeng.ac.id.githubprofile.R
import zizi.polbeng.ac.id.githubprofile.databinding.ActivityMainBinding
import zizi.polbeng.ac.id.githubprofile.helpers.Config
import zizi.polbeng.ac.id.githubprofile.models.GithubUser
import zizi.polbeng.ac.id.githubprofile.services.GithubUserService
import zizi.polbeng.ac.id.githubprofile.services.ServiceBuilder


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.btnSearchUserLogin.setOnClickListener {
            val userLogin = binding.etSearchUserLogin.text.toString()
            if (userLogin.isNotEmpty()) {
                searchUser(userLogin)
            }
        }
        searchUser(Config.DEFAULT_USER_LOGIN)
    }
    private fun searchUser(query: String){
        showLoading(true)
        Log.d(TAG, "getDataUserProfileFromAPI: start...")
        val githubUserService: GithubUserService = ServiceBuilder.buildService(GithubUserService::class.java)
        val requestCall: Call<GithubUser> =
            githubUserService.loginUser(Config.PERSONAL_ACCESS_TOKEN, query)
        requestCall.enqueue(object : retrofit2.Callback<GithubUser> {
            override fun onResponse(call: Call<GithubUser>, response: retrofit2.Response<GithubUser>) {
                showLoading(false)
                if(response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        setUserData(result)
                    }
                    Log.d(TAG, "getDataUserFromAPI: onResponse finish...")
                }else{
                    binding.tvUser.text = "User Not Found"
                    GlideApp.with(applicationContext)
                        .load(R.drawable.ic_baseline_broken_image_24)
                        .into(binding.imgUser)
                    Log.d(TAG, "getDataUserFromAPI: onResponse failed...")
                }
            }
            override fun onFailure(call: Call<GithubUser>, t: Throwable) {
                showLoading(false)
                Log.d(TAG, "getDataUserFromAPI: onFailure ${t.message}...")
            }
        })
    }
    private fun setUserData(githubUser: GithubUser) {
        binding.tvUser.text = githubUser.toString()
        GlideApp.with(applicationContext)
            .load(githubUser.avatarUrl)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .error(R.drawable.ic_baseline_broken_image_24))
            .into(binding.imgUser)
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}