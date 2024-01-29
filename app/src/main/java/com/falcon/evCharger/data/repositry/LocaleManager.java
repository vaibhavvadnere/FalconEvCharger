package com.falcon.evCharger.data.repositry;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;


import com.falcon.evCharger.EVMainActivity;

import java.util.Locale;

public class LocaleManager {
    public static final String PREF_KEY_LANGUAGE = "pref_key_language";
    public static final String PREF_KEY_LANGUAGE_CODE = "pref_key_language_code";
    private static final String PREF_KEY_CHANGE_LANGUAGE_REQUIRED = "pref_key_change_language_required";

    public static Context setLocale(EVMainActivity context, Locale locale) {
        persistLocale(locale);

//        Locale locale = new Locale("en");

        /*Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getBaseContext().getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());*/

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context, locale);
        }
        return updateResourcesLegacy(context, locale);*/

        if (context != null) {

//            Locale newLocale = new Locale(language);

            Resources res = context.getResources();
            Configuration configuration = res.getConfiguration();

            int sdkInt = Build.VERSION.SDK_INT;

            Log.e("sdkIntLogLocale", ":" + sdkInt);

            if (sdkInt >= 28) {
                DisplayMetrics dm = res.getDisplayMetrics();
                configuration.setLocale(locale);
                res.updateConfiguration(configuration, dm);

                Resources applicationRes = context.getApplicationContext().getResources();

                Configuration applicationConf = applicationRes.getConfiguration();
                applicationConf.setLocale(locale);

                applicationRes.updateConfiguration(applicationConf, applicationRes.getDisplayMetrics());

            } else if (sdkInt >= 24) {
                DisplayMetrics dm = res.getDisplayMetrics();
                Configuration conf = res.getConfiguration();
                conf.setLocale(locale);
                res.updateConfiguration(conf, dm);

            } /*else if (sdkInt >= 17) {
                configuration.setLocale(locale);
                context = context.createConfigurationContext(configuration);
                res.updateConfiguration(configuration, res.getDisplayMetrics());
            }*/ else {
                configuration.locale = locale;
                res.updateConfiguration(configuration, res.getDisplayMetrics());
            }

        }

        return context;
    }

    public static Context setDefaultLocale(EVMainActivity context) {
        Locale locale = getDefaultLocale();
        return setLocale(context, locale);
    }

    private static Context updateResources(Context context, Locale locale) {
        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);

        return context.createConfigurationContext(configuration);
    }

    @SuppressWarnings("deprecation")
    private static Context updateResourcesLegacy(Context context, Locale locale) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        configuration.setLayoutDirection(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        return context;
    }

    public static Locale getLocale(Context context) {

//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//        String localLanguage = preferences.getString(PREF_KEY_LANGUAGE, "");
//        Log.e("languageLogs", " : 1 :" + localLanguage);

        SharePrefRepo sharePrefRepo = SharePrefRepo.getInstance();

        String language = "";
        language = sharePrefRepo.getString(PREF_KEY_LANGUAGE);

        if (language != null) {
            Log.e("languageLogs", " : 2 :" + language);
            return Locale.forLanguageTag(language);
        } else {
            return Locale.forLanguageTag("");
        }
    }

    public static String getLanguageCode() {
        /*SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String languageCode = preferences.getString(PREF_KEY_LANGUAGE_CODE, "");*/
        SharePrefRepo sharePrefRepo = SharePrefRepo.getInstance();
        String languageCode = "";

        languageCode = sharePrefRepo.getString(PREF_KEY_LANGUAGE_CODE);

        if (languageCode != null && !languageCode.equals("")) {
            Log.e("languageCodeLogs", ":" + languageCode);
            return languageCode;
        } else
            return "1";

    }

    public static Boolean getChangeLocaleRequired() {

        /*SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean languageChangeRequired = preferences.getBoolean(PREF_KEY_CHANGE_LANGUAGE_REQUIRED, false);*/

        SharePrefRepo sharePrefRepo = SharePrefRepo.getInstance();
        boolean languageChangeRequired = sharePrefRepo.getBoolean(PREF_KEY_CHANGE_LANGUAGE_REQUIRED);

        Log.e("lanChangeReqLog", ":" + languageChangeRequired);

        return languageChangeRequired;
    }

    public static void setChangeLocaleRequired(boolean changeLanguageRequired) {

        SharePrefRepo sharePrefRepo = SharePrefRepo.getInstance();
        sharePrefRepo.putBoolean(PREF_KEY_CHANGE_LANGUAGE_REQUIRED, changeLanguageRequired);

        /*SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(PREF_KEY_CHANGE_LANGUAGE_REQUIRED, changeLanguageRequired);
        editor.apply();*/
    }

    private static void persistLocale(Locale locale) {
        Log.e("", "");
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = sharePrefRepo.edit();

        SharePrefRepo sharePrefRepo = SharePrefRepo.getInstance();
        sharePrefRepo.putString(PREF_KEY_LANGUAGE, locale.toLanguageTag());

//        editor.apply();
    }

    public static void persistLanguageCode(String languageCode) {
        Log.e("PersistLanguageCodeLog", "" + languageCode);
        /*SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();*/

        SharePrefRepo sharePrefRepo = SharePrefRepo.getInstance();
        sharePrefRepo.putString(PREF_KEY_LANGUAGE_CODE, languageCode);

//        editor.apply();
    }

    private static Locale getDefaultLocale() {
        // Return your desired default locale here
        return Locale.US;
    }
}
