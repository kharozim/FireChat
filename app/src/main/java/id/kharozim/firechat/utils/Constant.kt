package id.kharozim.firechat.utils

import id.kharozim.firechat.BuildConfig

class Constant {
    companion object {

        const val NOTIFICATION_ID = 123
        const val NOTIFICATION_CHANNEL_NAME = "Android Task Push Notification"
        const val NOTIFICATION_CHANNEL_ID = "${BuildConfig.APPLICATION_ID}.fcm"
        const val API_KEY = "AAAAN3v6kPU:APA91bHAq91ICxNJ1VaMYNAdLFBHltKaA6m8q-F1OQxLiAcN9ElzjwR6bD49UKCnWnEqcQztqsScPpbRBjRyfvy1HD7eqA2ylB5rxsFBRhQAiP8miuP6FIVl1zN5K-GB_oNBt8oz3okn"
        const val COLLECTION = "users"


        const val PREF_IS_LOGIN = "PREF_IS_LOGIN"
        const val PREF_TOKEN_KEY = "PREF_TOKEN_KEY"
        const val PREF_UID_KEY = "PREF_UID_KEY"


    }

}