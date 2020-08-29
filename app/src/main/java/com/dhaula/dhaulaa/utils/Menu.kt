package com.dhaula.dhaulaa.utils

import androidx.fragment.app.Fragment
import com.dhaula.dhaulaa.ui.fragment.*

object Menu {
      const val vegetables = 0
      const val fruits = 1
      const val news_and_articles = 3
      const val become_member = 4


//      const val icon_news = "https://images.unsplash.com/photo-1504711434969-e33886168f5c?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"
      const val icon_news = "https://lh3.googleusercontent.com/pw/ACtC-3eC4MSZ3Lh_6ZIZ_UHF0U4UE1h4hgyIY9fjW2uzZsBVq6z6Euxr3NOE_MgUh3gY8adB7_K5eebG60mNHMvE0CPZYxkFaw8yH1ya1VBArDwNa7MXkedInB3eFj5eInuqbdzmyEpx0fGn3C3fCZAyEW6fZw=w500-h450-no?authuser=0"
      const val icon_vegetables = "https://lh3.googleusercontent.com/pw/ACtC-3fMEBgAlWERTrx19b0ypj2BlW5StanPR-s_xMmE_eeBFy8WpDVgJeCSxe3b9M7Aewmr0EiGDb5ZtMAtKsQK1P3AsAlZTU67T0FUcDOXWkMWMbQNz7X1hQnYHR4IXByJAp688oqeGcWDPEP89Imqrt1B9Q=w500-h450-no?authuser=0"
      const val icon_fruits = "https://lh3.googleusercontent.com/pw/ACtC-3fKOrlkHdN16RA_4QHHeAUSSKNrS0SWs_IDM5FdfXXXj3O3lYHiu1cXP4VtLz-lzu81slHa58awuC7ZpLHuwFxqzwbKtCrFn2y1QfiAHKYwKwRRjSwXt6OKrhaM4L8B5xMqtJq0f26cH6FKzdsfla9VaA=w500-h450-no?authuser=0"
      const val icon_member = "https://lh3.googleusercontent.com/pw/ACtC-3eR6N8AsTvcxpoCDZOjqO8MdSWKleE0wdtTXy0CnNuZvIlDEXK6sYAwH_fF8YuB6kKBw5YytRXylLeDzDBQZiXgpvOOq-tttd8WyDQIz_7pIS_eDTC_mk5QfpA09qN1klisLbR2gKENPHwDuPpzxDmpPg=w500-h450-no?authuser=0"

    fun getMenuName(menuId:Int):String{
        return when(menuId){
            vegetables->{"Vegetables"}
            fruits->{"Fruits"}
            news_and_articles->{"News and Articles"}
            become_member->{"Become a member"}
            else->{""}
        }
    }

    fun getMenuNavigation(menuId:Int):Fragment{
        return when(menuId){
            news_and_articles->{FragmentNews()}
            become_member->{FragmentPartner()}
            else->{FragmentPacks()}
        }
    }

    fun getMenuIcon(menuId:Int):String{
        return when(menuId){
            vegetables->{ icon_vegetables}
            fruits->{icon_fruits}
            news_and_articles->{icon_news}
            become_member->{ icon_member}
            else->{UIUtils.image}
        }
    }


}