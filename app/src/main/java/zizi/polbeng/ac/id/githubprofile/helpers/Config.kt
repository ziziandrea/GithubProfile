package zizi.polbeng.ac.id.githubprofile.helpers

import zizi.polbeng.ac.id.githubprofile.BuildConfig
import zizi.polbeng.ac.id.githubprofile.helpers.Config.Companion.PERSONAL_ACCESS_TOKEN

class Config {
    companion object{
        const val SPLASH_SCREEN_DELAY:Long = 3000
        const val BASE_URL ="https://api.github.com"
        const val DEFAULT_USER_LOGIN = "ziziandrea"
        const val PERSONAL_ACCESS_TOKEN = "token ${BuildConfig.ACCESS_TOKEN}"
    }
}