package com.marcel7126.rozvrhdpg

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.provider.CalendarContract
import android.view.*
import androidx.annotation.StringRes
import com.marcel7126.rozvrhdpg.databinding.*
import java.net.URL
import java.util.*

object UtilityOther {

    object Strings {
        fun get(@StringRes stringRes: Int, vararg formatArgs: Any = emptyArray()): String {
            return App.instance.getString(stringRes, *formatArgs)
        }
    }

    var nacteno = false

    var loadCheck = true

    lateinit var tableData : Array<Array<Array<String>>>

    var celyTydenNacteno = false
    lateinit var celyTyden : Array<Array<Array<String>>>

    val spreadsheetsURLs: Map<String, URL> = mapOf(
        "subjects" to URL("https://docs.google.com/spreadsheets/d/e/2PACX-1vQBA8ljjavuv0Ww4cXPHMoEiQIIxUdiAdqvmw1fsdITJIzanCL5CJ_-yThEhodVtDAqi0kWIxsyondI/pub?gid=1204752177&single=true&output=csv"),
        "teachers" to URL("https://docs.google.com/spreadsheets/d/e/2PACX-1vQBA8ljjavuv0Ww4cXPHMoEiQIIxUdiAdqvmw1fsdITJIzanCL5CJ_-yThEhodVtDAqi0kWIxsyondI/pub?gid=2118805848&single=true&output=csv"),
        "schoolClassRooms" to URL("https://docs.google.com/spreadsheets/d/e/2PACX-1vQBA8ljjavuv0Ww4cXPHMoEiQIIxUdiAdqvmw1fsdITJIzanCL5CJ_-yThEhodVtDAqi0kWIxsyondI/pub?gid=1306057306&single=true&output=csv"),
        "timeSchedule" to URL("https://docs.google.com/spreadsheets/d/e/2PACX-1vQBA8ljjavuv0Ww4cXPHMoEiQIIxUdiAdqvmw1fsdITJIzanCL5CJ_-yThEhodVtDAqi0kWIxsyondI/pub?gid=1800710376&single=true&output=csv"),
        "suplo" to URL("https://docs.google.com/spreadsheets/d/e/2PACX-1vQBA8ljjavuv0Ww4cXPHMoEiQIIxUdiAdqvmw1fsdITJIzanCL5CJ_-yThEhodVtDAqi0kWIxsyondI/pub?gid=1952245285&single=true&output=csv"),
        "celyTyden" to URL("https://docs.google.com/spreadsheets/d/e/2PACX-1vQBA8ljjavuv0Ww4cXPHMoEiQIIxUdiAdqvmw1fsdITJIzanCL5CJ_-yThEhodVtDAqi0kWIxsyondI/pub?gid=386978828&single=true&output=csv")
    )

    val subjectsStringIds: Map<String, Int> = mapOf(
        "Af" to R.string.af,
        "Aj" to R.string.aj,
        "Ak" to R.string.ak,
        "Al" to R.string.al,
        "Ar" to R.string.ar,
        "As" to R.string.as_,
        "Bc" to R.string.bc,
        "Bi" to R.string.bi,
        "BiR" to R.string.bir,
        "BiZ" to R.string.biz,
        "Čg" to R.string.cg,
        "Čjk" to R.string.cjk,
        "Čj" to R.string.cj,
        "D" to R.string.d,
        "Dk" to R.string.dk,
        "DS" to R.string.ds,
        "Dt" to R.string.dt,
        "Dv" to R.string.dv,
        "Eb" to R.string.eb,
        "Ek" to R.string.ek,
        "Eko" to R.string.eko,
        "Eto" to R.string.eto,
        "Evs" to R.string.evs,
        "FCE" to R.string.fce,
        "Fi" to R.string.fi,
        "Fj" to R.string.fj,
        "FjN" to R.string.fjn,
        "Fy" to R.string.fy,
        "Ge" to R.string.ge,
        "Hs" to R.string.hs,
        "Hv" to R.string.hv,
        "Ch" to R.string.ch,
        "If" to R.string.if_,
        "Kek" to R.string.kek,
        "La" to R.string.la,
        "LtB" to R.string.ltb,
        "LtC" to R.string.ltc,
        "Li" to R.string.li,
        "Ls" to R.string.ls,
        "M" to R.string.m,
        "MD" to R.string.md,
        "Mm" to R.string.mm,
        "Mr" to R.string.mr,
        "Nj" to R.string.nj,
        "Nk" to R.string.nk,
        "Ov" to R.string.ov,
        "Pg" to R.string.pg,
        "Pp" to R.string.pp,
        "PrE" to R.string.pre,
        "Ps" to R.string.ps,
        "Re" to R.string.re,
        "Svs" to R.string.svs,
        "Šj" to R.string.sj,
        "Šk" to R.string.sk,
        "ŠP" to R.string.sp,
        "Tv" to R.string.tv,
        "Ve" to R.string.ve,
        "Vs" to R.string.vs,
        "Vv" to R.string.vv,
        "Vz" to R.string.vz,
        "Ze" to R.string.ze,
        "Dg" to R.string.dg,
        "Tvc" to R.string.tvc,
        "Tvd" to R.string.tvd,
        "Th" to R.string.th,
        "oběd" to R.string.lunch_break
    )

    val teachers: Map<String, String> = mapOf(  ///mozna bude lepsi zkratit krestni mena na zkratky?? nebo treba jen prepsat na prof.
        "An" to "Hana Antonínová Hegerová",
        "Be" to "Petra Beranová",
        "Bl" to "Marek Blecha",
        "Bv" to "Anna Blechová",
        "Co" to "Ivam Cociňa", // nerad bych napsal spatne jak je jeho cele jmeno spravne?
        "Cs" to "Kateřina Csibová",
        "Da" to "Marie Davidová",
        "Er" to "Vladimíra Erhartová",
        "Hf" to "Marcela Hefferová",
        "Ho" to "Daniela Horáková",
        "Hn" to "Dalibor Hanzal",
        "Ja" to "Jiří Jansa",
        "Ka" to "Nikola Kárníková",
        "Kd" to "Michal Kadeřábek",
        "Mc" to "Petra Cociňa Mocová",
        "Pa" to "Radka Pašková",
        "Pk" to "Jana Pikousová Doležalová",
        "Pl" to "Zbyněk Plocar",
        "Qu" to "Petra Quirenzová",
        "Rg" to "Stanislava Rygálová",
        "Ro" to "Dana Rosická",
        "Sa" to "Petra Sas Smejkalová",
        "Sv" to "Alžběta Sakařová",
        "St" to "Radomír Stupka",
        "Šr" to "Alena Šáfrová",
        "Šp" to "Ondřej Šíp",
        "Šv" to "Klára Švejdová",
        "Tm" to "Veronika Tůmová",
        "Tu" to "Martin Turčík",
        "Za" to "Vanda Zaplatílková Hutařová",
        "Ži" to "Tomáš Žižka",
        "Mo" to "Hana Moosová",
        "Hb" to "R. Hebein",
        "Je" to "Kamila Jech Koldinská",
        "Jn" to "M. Janosik Bielski",
        "Ma" to "T. Marešová",
        "Mn" to "L. Macounová",
        "Kb" to "P. Knobloch",
        "Pe" to "Z.Pechová",
        "Rt" to "D. Rytina",
        "Sh" to "Petra Stěhulová",
        "Se" to "J. Stejskalová",
        "So" to "J. Souček",
        "Sw" to "Swallow School",
        "Ši" to "Jaroslava Šiftová",

        "Hana Antonínová Hegerová" to "An",
        "Petra Beranová" to "Be",
        "Marek Blecha" to "Bl",
        "Anna Blechová" to "Bv",
        "Ivam Cociňa" to "Co",
        "Kateřina Csibová" to "Cs",
        "Marie Davidová" to "Da",
        "Vladimíra Erhartová" to "Er",
        "Marcela Hefferová" to "Hf",
        "Daniela Horáková" to "Ho",
        "Dalibor Hanzal" to "Hn",
        "Jiří Jansa" to "Ja",
        "Nikola Kárníková" to "Ka",
        "Michal Kadeřábek" to "Kd",
        "Petra Cociňa Mocová" to "Mc",
        "Radka Pašková" to "Pa",
        "Jana Pikousová Doležalová" to "Pk",
        "Zbyněk Plocar" to "Pl",
        "Petra Quirenzová" to "Qu",
        "Stanislava Rygálová" to "Rg",
        "Dana Rosická" to "Ro",
        "Petra Sas Smejkalová" to "Sa",
        "Alžběta Sakařová" to "Sv",
        "Radomír Stupka" to "St",
        "Alena Šáfrová" to "Šr",
        "Ondřej Šíp" to "Šp",
        "Klára Švejdová" to "Šv",
        "Veronika Tůmová" to "Tm",
        "Martin Turčík" to "Tu",
        "Vanda Zaplatílková Hutařová" to "Za",
        "Tomáš Žižka" to "Ži",
        "Hana Moosová" to "Mo",
        "R. Hebein" to "Hb",
        "Kamila Jech Koldinská" to "Je",
        "M. Janosik Bielski" to "Jn",
        "T. Marešová" to "Ma",
        "L. Macounová" to "Mn",
        "P. Knobloch" to "Kb",
        "Z.Pechová" to "Pe",
        "D. Rytina" to "Rt",
        "Petra Stěhulová" to "Sh",
        "J. Stejskalová" to "Se",
        "J. Souček" to "So",
        "Swallow School" to "Sw",
        "Jaroslava Šiftová" to "Ši"
    )

    val arrayTeachersShort: Array<String> = arrayOf(
        "An",
        "Be",
        "Bl",
        "Bv",
        "Co",
        "Cs",
        "Da",
        "Er",
        "Hf",
        "Ho",
        "Hn",
        "Ja",
        "Ka",
        "Kd",
        "Mc",
        "Pa",
        "Pk",
        "Pl",
        "Qu",
        "Rg",
        "Ro",
        "Sa",
        "Sv",
        "St",
        "Šr",
        "Šp",
        "Šv",
        "Tm",
        "Tu",
        "Za",
        "Ži",
        "Mo",
        "Hb",
        "Je",
        "Jn",
        "Ma",
        "Mn",
        "Kb",
        "Pe",
        "Rt",
        "Sh",
        "Se",
        "So",
        "Sw",
        "Ši"
    )

    val zvoneni: Map<Int, Array<String>> = mapOf(
        0 to arrayOf("8:00", "8:45", "8", "00"),
        1 to arrayOf("8:55", "9:40", "8", "55"),
        2 to arrayOf("10:00", "10:45", "10", "00"),
        3 to arrayOf("10:55", "11:40", "10", "55"),
        4 to arrayOf("11:50", "12:35", "11", "50"),
        5 to arrayOf("12:40", "13:25", "12", "40"),
        6 to arrayOf("13:30", "14:15", "13", "30"),
        7 to arrayOf("14:25", "15:10", "14", "25"),
        8 to arrayOf("15:15", "16:00", "15", "15"),
        9 to arrayOf("16:05", "16:50", "16", "05"),
        10 to arrayOf("16:55", "17:40", "16", "55")
    )

    val pravidla: Map<String, String> = mapOf(
        "Vv" to "At",
        "Ve" to "At",
        "Vs" to "At",
        "Hv" to "Hv",
        "If" to "IT",
        "Dt" to "IT",
        "Pg" to "IT",
        "Ch" to "L",
        "Fy" to "L",
        "Af" to "L",
        "Mm" to "L",
        "MD" to "L",
        "LtC" to "L",
        "Bi" to "Př",
        "Eko" to "Př",
        "Eto" to "Př",
        "As" to "Př",
        "Eb" to "Př",
        "LtB" to "Př",
        "BiZ" to "Př",
        "BiR" to "Př"
    )

    var year = "ok"

    val classrooms: Map<String, Array<Any>> = mapOf(
        "Pr" to arrayOf("Prima", 1),
        "Sk" to arrayOf("Sekunda", 1),
        "Te" to arrayOf("Tercie", 2),
        "Kr" to arrayOf("Kvarta", 0),
        "Kn" to arrayOf("Kvinta", 2),
        "Sx" to arrayOf("Sexta", 0),
        "Sp" to arrayOf("Septima", 2),
        "Ok" to arrayOf("Oktáva", 0),
        "I.R" to arrayOf("I. ročník", 1),
        "II.R" to arrayOf("II. ročník", 0),
        "III.R" to arrayOf("III. ročník", 2),
        "IV.R" to arrayOf("IV. ročník", 2),
        "I." to arrayOf("I. ročník", 1),
        "II." to arrayOf("II. ročník", 0),
        "III." to arrayOf("III. ročník", 2),
        "IV." to arrayOf("IV. ročník", 2),
        "I" to arrayOf("I. ročník", 1),
        "II" to arrayOf("II. ročník", 0),
        "III" to arrayOf("III. ročník", 2),
        "IV" to arrayOf("IV. ročník", 2),
        "i" to arrayOf("I. ročník", 1),
        "ii" to arrayOf("II. ročník", 0),
        "iii" to arrayOf("III. ročník", 2),
        "iv" to arrayOf("IV. ročník", 2),
        "At" to arrayOf("Ateliér", 0),
        "Hv" to arrayOf("Hudebna", 2),
        "K" to arrayOf("Akvárium", 0),
        "L" to arrayOf("Laboratoř", 1),
        "Př" to arrayOf("Přírodovědná učebna", 2),
        "Su" to arrayOf("Studovna", 2),
        "U1" to arrayOf("Učebna 1", 1),
        "U2" to arrayOf("Učebna 2", 2),
        "U2" to arrayOf("Učebna 3", 2),
        "C" to arrayOf("Počítače", -1),
        "IT" to arrayOf("Inf. technologie", 1),
        "Jv" to arrayOf("Velká jazyková učebna", -1),
        "Sv" to arrayOf("Společenskovědní učebna", -1),
        "Kh" to arrayOf("Knihovna", -1),
        "Stu" to arrayOf("Studovna", 2),

        "pr" to arrayOf("Prima", 1),
        "sk" to arrayOf("Sekunda", 1),
        "te" to arrayOf("Tercie", 2),
        "kr" to arrayOf("Kvarta", 0),
        "kn" to arrayOf("Kvinta", 2),
        "sx" to arrayOf("Sexta", 0),
        "sp" to arrayOf("Septima", 2),
        "ok" to arrayOf("Oktáva", 0),
        "i" to arrayOf("I. ročník", 1),
        "ii" to arrayOf("II. ročník", 0),
        "iii" to arrayOf("III. ročník", 2),
        "iv" to arrayOf("IV. ročník", 2),
        "i" to arrayOf("I. Ročník", 1),
        "ii" to arrayOf("II. Ročník", 0),
        "iii" to arrayOf("III. Ročník", 2),
        "iv" to arrayOf("IV. Ročník", 2),

        "Prima" to arrayOf("pr", 1),
        "Sekunda" to arrayOf("sk", 1),
        "Tercie" to arrayOf("te", 2),
        "Kvarta" to arrayOf("kr", 0),
        "Kvinta" to arrayOf("kn", 2),
        "Sexta" to arrayOf("sx", 0),
        "Septima" to arrayOf("sp", 2),
        "Oktáva" to arrayOf("ok", 0),
        "I. ročník" to arrayOf("i", 1),
        "II. ročník" to arrayOf("ii", 0),
        "III. ročník" to arrayOf("iii", 2),
        "IV. ročník" to arrayOf("iv", 2),
        "I. Ročník" to arrayOf("i", 1),
        "II. Ročník" to arrayOf("ii", 0),
        "III. Ročník" to arrayOf("iii", 2),
        "IV. Ročník" to arrayOf("iv", 2)
    )

    val rocniky_short : Map<String, String> = mapOf(
        "pr" to "Pr",
        "sk" to "Sk",
        "te" to "Te",
        "kr" to "Kr",
        "kn" to "Kn",
        "sx" to "Sx",
        "sp" to "Sp",
        "ok" to "Ok",
        "i" to "I",
        "ii" to "II",
        "iii" to "III",
        "iv" to "IV"
    )

    val classroomsToShort: Map<String, String> = mapOf(
        "Prima" to "Pr",
        "Sekunda" to "Sk",
        "Tercie" to "Te",
        "Kvarta" to "Kr",
        "Kvinta" to "Kn",
        "Sexta" to "Sx",
        "Septima" to "Sp",
        "Oktáva" to "Ok",
        "I. ročník" to "I.",
        "II. ročník" to "II.",
        "III. ročník" to "III.",
        "IV. ročník" to "IV."
    )

    val commonNotesStringIdsMap: Map<String, Int> = mapOf(
        "TT" to R.string.tt,
        "OTT" to R.string.ott,
        "opr." to R.string.ott,
        "odp" to R.string.odp,
        "odpadá" to R.string.odp,
        "presun" to R.string.presun,
        "misto #" to R.string.misto,
        "spoj" to R.string.spoj,
        "TH" to R.string.th,
        "Th" to R.string.th,
        "Sw" to R.string.sw,
        "*" to R.string.stars1,
        "**" to R.string.stars2,
        "***" to R.string.stars3,
        "****" to R.string.stars4,
        "*****" to R.string.stars5
    )

    val arrayStudents = arrayOf(
        "Prima",
        "Sekunda",
        "Tercie",
        "Kvarta",
        "Kvinta",
        "Sexta",
        "Septima",
        "Oktáva",
        "I. ročník",
        "II. ročník",
        "III. ročník",
        "IV. ročník"
    )

    val arrayStudentsShort = arrayOf(
        "Pr",
        "Sk",
        "Te",
        "Kr",
        "Kn",
        "Sx",
        "Sp",
        "Ok",
        "I.",
        "II.",
        "III.",
        "IV."
    )

    val arrayTeachers = arrayOf(
        "Hana Antonínová Hegerová",
        "Petra Beranová",
        "Marek Blecha",
        "Anna Blechová",
        "Ivam Cociňa",
        "Kateřina Csibová",
        "Marie Davidová",
        "Vladimíra Erhartová",
        "Marcela Hefferová",
        "Daniela Horáková",
        "Dalibor Hanzal",
        "Jiří Jansa",
        "Nikola Kárníková",
        "Michal Kadeřábek",
        "Petra Cociňa Mocová",
        "Radka Pašková",
        "Jana Pikousová Doležalová",
        "Zbyněk Plocar",
        "Petra Quirenzová",
        "Stanislava Rygálová",
        "Dana Rosická",
        "Petra Sas Smejkalová",
        "Alžběta Sakařová",
        "Radomír Stupka",
        "Alena Šáfrová",
        "Ondřej Šíp",
        "Klára Švejdová",
        "Veronika Tůmová",
        "Martin Turčík",
        "Vanda Zaplatílková Hutařová",
        "Tomáš Žižka",
        "Hana Moosová",
        "R. Hebein",
        "Kamila Jech Koldinská",
        "M. Janosik Bielski",
        "T. Marešová",
        "L. Macounová",
        "P. Knobloch",
        "Z.Pechová",
        "D. Rytina",
        "Petra Stěhulová",
        "J. Stejskalová",
        "J. Souček",
        "Swallow School",
        "Jaroslava Šiftová"
    )

    val daysStringIds = arrayOf(
        R.string.day_short_mon,
        R.string.day_short_tue,
        R.string.day_short_wed,
        R.string.day_short_thu,
        R.string.day_short_fri
    )




}