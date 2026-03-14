package com.example.moodtracker

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import java.text.SimpleDateFormat
import java.util.*

data class Str(
    val appTitle: String, val today: String, val noMood: String,
    val pickBelow: String, val howAreYou: String, val noteLbl: String,
    val noteHint: String, val saveBtn: String, val savedBtn: String,
    val weekLabel: String, val toast: String, val addNote: String,
    val days: List<String>, val isRtl: Boolean = false
)

data class MoodItem(val emoji: String, val labels: Map<String, String>)

val MOODS = listOf(
    MoodItem("😄", mapOf("af" to "Uitstekend", "sq" to "Shkëlqyeshëm", "am" to "ድንቅ", "ar" to "رائع", "hy" to "Հիանալի", "az" to "Əla", "eu" to "Bikain", "be" to "Выдатна", "bn" to "দারুণ", "bs" to "Odlično", "bg" to "Чудесно", "ca" to "Excel·lent", "zh" to "很棒", "zh-TW" to "很棒", "hr" to "Odlično", "cs" to "Skvěle", "da" to "Fremragende", "nl" to "Geweldig", "en" to "Great", "et" to "Suurepärane", "fil" to "Napakagaling", "fi" to "Mahtava", "fr" to "Excellent", "gl" to "Excelente", "ka" to "შესანიშნავი", "de" to "Super", "el" to "Υπέροχα", "gu" to "ઉત્તમ", "iw" to "מצוין", "hi" to "बढ़िया", "hu" to "Remek", "is" to "Frábærlega", "id" to "Luar biasa", "it" to "Ottimo", "ja" to "最高", "kn" to "ಅದ್ಭುತ", "kk" to "Тамаша", "km" to "ល្អណាស់", "ko" to "최고", "ky" to "Мыкты", "lo" to "ດີເລີດ", "lv" to "Lieliski", "lt" to "Puikiai", "mk" to "Одлично", "ms" to "Hebat", "ml" to "അടിപൊളി", "mr" to "उत्कृष्ट", "mn" to "Гайхалтай", "my" to "အကောင်းဆုံး", "ne" to "उत्कृष्ट", "nb" to "Utmerket", "or" to "ଅସାଧାରଣ", "fa" to "عالی", "pl" to "Świetnie", "pt" to "Ótimo", "pt-BR" to "Ótimo", "pa" to "ਬਹੁਤ ਵਧੀਆ", "ro" to "Excelent", "ru" to "Отлично", "sr" to "Одлично", "si" to "විශිෂ්ටයි", "sk" to "Skvelé", "sl" to "Odlično", "es" to "Excelente", "es-419" to "Excelente", "sw" to "Vizuri sana", "sv" to "Utmärkt", "ta" to "அருமை", "te" to "అద్భుతం", "th" to "ยอดเยี่ยม", "tr" to "Mükemmel", "tk" to "Ajaýyp", "uk" to "Чудово", "ur" to "زبردست", "ug" to "ئالاھىدە", "uz" to "Ajoyib", "vi" to "Tuyệt vời", "cy" to "Gwych", "zu" to "Kuhle kakhulu")),
    MoodItem("🙂", mapOf("af" to "Goed", "sq" to "Mirë", "am" to "ጥሩ", "ar" to "جيد", "hy" to "Լավ", "az" to "Yaxşı", "eu" to "Ondo", "be" to "Добра", "bn" to "ভালো", "bs" to "Dobro", "bg" to "Добре", "ca" to "Bé", "zh" to "不错", "zh-TW" to "不錯", "hr" to "Dobro", "cs" to "Dobře", "da" to "Godt", "nl" to "Goed", "en" to "Good", "et" to "Hea", "fil" to "Mabuti", "fi" to "Hyvä", "fr" to "Bien", "gl" to "Ben", "ka" to "კარგი", "de" to "Gut", "el" to "Καλά", "gu" to "સારું", "iw" to "טוב", "hi" to "अच्छा", "hu" to "Jó", "is" to "Vel", "id" to "Baik", "it" to "Bene", "ja" to "良い", "kn" to "ಒಳ್ಳೆಯದು", "kk" to "Жақсы", "km" to "ល្អ", "ko" to "좋음", "ky" to "Жакшы", "lo" to "ດີ", "lv" to "Labi", "lt" to "Gerai", "mk" to "Добро", "ms" to "Baik", "ml" to "നല്ലത്", "mr" to "चांगले", "mn" to "Сайн", "my" to "ကောင်းသည်", "ne" to "राम्रो", "nb" to "Bra", "or" to "ଭଲ", "fa" to "خوب", "pl" to "Dobrze", "pt" to "Bom", "pt-BR" to "Bom", "pa" to "ਚੰਗਾ", "ro" to "Bine", "ru" to "Хорошо", "sr" to "Добро", "si" to "හොඳයි", "sk" to "Dobre", "sl" to "Dobro", "es" to "Bien", "es-419" to "Bien", "sw" to "Nzuri", "sv" to "Bra", "ta" to "நல்லது", "te" to "మంచిది", "th" to "ดี", "tr" to "İyi", "tk" to "Gowy", "uk" to "Добре", "ur" to "اچھا", "ug" to "ياخشى", "uz" to "Yaxshi", "vi" to "Tốt", "cy" to "Da", "zu" to "Kuhle")),
    MoodItem("😐", mapOf("af" to "Okay", "sq" to "Okay", "am" to "ይሆናል", "ar" to "عادي", "hy" to "Նորմալ", "az" to "Normal", "eu" to "Ongi", "be" to "Нармальна", "bn" to "ঠিক আছে", "bs" to "Okej", "bg" to "Нормално", "ca" to "Regular", "zh" to "一般", "zh-TW" to "普通", "hr" to "U redu", "cs" to "OK", "da" to "Okay", "nl" to "Oké", "en" to "Okay", "et" to "Okei", "fil" to "Okay", "fi" to "Ok", "fr" to "Correct", "gl" to "Regular", "ka" to "ნორმალური", "de" to "OK", "el" to "Εντάξει", "gu" to "ઠીક", "iw" to "בסדר", "hi" to "ठीक है", "hu" to "Oké", "is" to "Sæmilega", "id" to "Oke", "it" to "Ok", "ja" to "まあまあ", "kn" to "ಸರಿ", "kk" to "Орташа", "km" to "ធម្មតា", "ko" to "괜찮음", "ky" to "Болду", "lo" to "ໃຊ້ໄດ້", "lv" to "Labi", "lt" to "Neblogai", "mk" to "Окај", "ms" to "Okay", "ml" to "ശരി", "mr" to "ठीक आहे", "mn" to "Тийм болно", "my" to "ဖြစ်နိုင်သည်", "ne" to "ठीक छ", "nb" to "Ok", "or" to "ଠିକ ଅଛି", "fa" to "معمولی", "pl" to "OK", "pt" to "Ok", "pt-BR" to "Ok", "pa" to "ਠੀਕ ਹੈ", "ro" to "OK", "ru" to "Нормально", "sr" to "Океј", "si" to "හරි", "sk" to "OK", "sl" to "OK", "es" to "Regular", "es-419" to "Regular", "sw" to "Sawa", "sv" to "Ok", "ta" to "சரி", "te" to "సరే", "th" to "โอเค", "tr" to "Fena değil", "tk" to "Bolýar", "uk" to "Нормально", "ur" to "ٹھیک ہے", "ug" to "بولىدۇ", "uz" to "Normal", "vi" to "Ổn", "cy" to "Iawn", "zu" to "Kulungile")),
    MoodItem("😞", mapOf("af" to "Hartseer", "sq" to "I trishtuar", "am" to "አዝኛለሁ", "ar" to "حزين", "hy" to "Տխուր", "az" to "Kədərli", "eu" to "Triste", "be" to "Сумна", "bn" to "দুঃখিত", "bs" to "Tužno", "bg" to "Тъжно", "ca" to "Trist", "zh" to "难过", "zh-TW" to "難過", "hr" to "Tužno", "cs" to "Smutně", "da" to "Trist", "nl" to "Verdrietig", "en" to "Sad", "et" to "Kurb", "fil" to "Malungkot", "fi" to "Surullinen", "fr" to "Triste", "gl" to "Triste", "ka" to "სევდიანი", "de" to "Traurig", "el" to "Λυπημένος", "gu" to "દુઃખી", "iw" to "עצוב", "hi" to "उदास", "hu" to "Szomorú", "is" to "Dapurlega", "id" to "Sedih", "it" to "Triste", "ja" to "悲しい", "kn" to "ದುಃಖ", "kk" to "Қайғылы", "km" to "សោកសៅ", "ko" to "슬픔", "ky" to "Кайгылуу", "lo" to "ເສົ້າ", "lv" to "Skumji", "lt" to "Liūdnai", "mk" to "Тажно", "ms" to "Sedih", "ml" to "സങ്കടം", "mr" to "दुःखी", "mn" to "Гунигтай", "my" to "ဝမ်းနည်းသည်", "ne" to "दुखी", "nb" to "Trist", "or" to "ଦୁଃଖୀ", "fa" to "غمگین", "pl" to "Smutno", "pt" to "Triste", "pt-BR" to "Triste", "pa" to "ਉਦਾਸ", "ro" to "Trist", "ru" to "Грустно", "sr" to "Тужно", "si" to "දුකයි", "sk" to "Smutno", "sl" to "Žalostno", "es" to "Triste", "es-419" to "Triste", "sw" to "Huzuni", "sv" to "Ledsen", "ta" to "சோகம்", "te" to "విచారం", "th" to "เศร้า", "tr" to "Üzgün", "tk" to "Gamgyn", "uk" to "Сумно", "ur" to "اداس", "ug" to "غەمگىن", "uz" to "G'amgin", "vi" to "Buồn", "cy" to "Trist", "zu" to "Lusizi")),
    MoodItem("😤", mapOf("af" to "Kwaad", "sq" to "I zemëruar", "am" to "ተናዶ", "ar" to "غاضب", "hy" to "Բարկ", "az" to "Əsəbi", "eu" to "Haserre", "be" to "Злосна", "bn" to "রাগান্বিত", "bs" to "Ljutito", "bg" to "Ядосано", "ca" to "Enfadat", "zh" to "生气", "zh-TW" to "生氣", "hr" to "Ljutito", "cs" to "Naštvaně", "da" to "Vred", "nl" to "Boos", "en" to "Angry", "et" to "Vihane", "fil" to "Galit", "fi" to "Vihainen", "fr" to "En colère", "gl" to "Enfadado", "ka" to "გაბრაზებული", "de" to "Wütend", "el" to "Θυμωμένος", "gu" to "ગુસ્સો", "iw" to "כועס", "hi" to "गुस्सा", "hu" to "Mérges", "is" to "Reiðilega", "id" to "Marah", "it" to "Arrabbiato", "ja" to "怒り", "kn" to "ಕೋಪ", "kk" to "Ашулы", "km" to "ខឹង", "ko" to "화남", "ky" to "Ачуулуу", "lo" to "ໃຈຮ້າຍ", "lv" to "Dusmīgi", "lt" to "Piktai", "mk" to "Лутито", "ms" to "Marah", "ml" to "ദേഷ്യം", "mr" to "रागावलेले", "mn" to "Уурласан", "my" to "ဒေါသ", "ne" to "रिसाएको", "nb" to "Sint", "or" to "ରାଗ", "fa" to "عصبانی", "pl" to "Złość", "pt" to "Irritado", "pt-BR" to "Irritado", "pa" to "ਗੁੱਸੇ ਵਿੱਚ", "ro" to "Supărat", "ru" to "Злость", "sr" to "Бесно", "si" to "කෝපයි", "sk" to "Naštvaný", "sl" to "Jezno", "es" to "Enfadado", "es-419" to "Enojado", "sw" to "Hasira", "sv" to "Arg", "ta" to "கோபம்", "te" to "కోపం", "th" to "โกรธ", "tr" to "Kızgın", "tk" to "Gaharly", "uk" to "Злість", "ur" to "غصہ", "ug" to "ئاچچىق", "uz" to "G'azablangan", "vi" to "Tức giận", "cy" to "Dig", "zu" to "Ukuthukuthela")),
)

val LANG_DISPLAY = mapOf(
    "af" to "Afrikaans",
    "sq" to "Shqip",
    "am" to "አማርኛ",
    "ar" to "العربية",
    "hy" to "Հայերեն",
    "az" to "Azərbaycan",
    "eu" to "Euskara",
    "be" to "Беларуская",
    "bn" to "বাংলা",
    "bs" to "Bosanski",
    "bg" to "Български",
    "ca" to "Català",
    "zh" to "中文(简)",
    "zh-TW" to "中文(繁)",
    "hr" to "Hrvatski",
    "cs" to "Čeština",
    "da" to "Dansk",
    "nl" to "Nederlands",
    "en" to "English",
    "et" to "Eesti",
    "fil" to "Filipino",
    "fi" to "Suomi",
    "fr" to "Français",
    "gl" to "Galego",
    "ka" to "ქართული",
    "de" to "Deutsch",
    "el" to "Ελληνικά",
    "gu" to "ગુજરાતી",
    "iw" to "עברית",
    "hi" to "हिन्दी",
    "hu" to "Magyar",
    "is" to "Íslenska",
    "id" to "Indonesia",
    "it" to "Italiano",
    "ja" to "日本語",
    "kn" to "ಕನ್ನಡ",
    "kk" to "Қазақ",
    "km" to "ខ្មែរ",
    "ko" to "한국어",
    "ky" to "Кыргыз",
    "lo" to "ລາວ",
    "lv" to "Latviešu",
    "lt" to "Lietuvių",
    "mk" to "Македонски",
    "ms" to "Melayu",
    "ml" to "മലയാളം",
    "mr" to "मराठी",
    "mn" to "Монгол",
    "my" to "မြန်မာ",
    "ne" to "नेपाली",
    "nb" to "Norsk",
    "or" to "ଓଡ଼ିଆ",
    "fa" to "فارسی",
    "pl" to "Polski",
    "pt" to "Português",
    "pt-BR" to "Português(BR)",
    "pa" to "ਪੰਜਾਬੀ",
    "ro" to "Română",
    "ru" to "Русский",
    "sr" to "Српски",
    "si" to "සිංහල",
    "sk" to "Slovenčina",
    "sl" to "Slovenščina",
    "es" to "Español",
    "es-419" to "Español(LA)",
    "sw" to "Kiswahili",
    "sv" to "Svenska",
    "ta" to "தமிழ்",
    "te" to "తెలుగు",
    "th" to "ภาษาไทย",
    "tr" to "Türkçe",
    "tk" to "Türkmençe",
    "uk" to "Українська",
    "ur" to "اردو",
    "ug" to "Uyghur",
    "uz" to "O'zbek",
    "vi" to "Tiếng Việt",
    "cy" to "Cymraeg",
    "zu" to "IsiZulu",
)

val STRINGS = mapOf(
    "af" to Str("My Bui", "VANDAG", "Nie gestel", "Kies jou bui hieronder", "HOE VOEL JY?", "NOTA", "Wat het vandag gebeur?", "Stoor", "Gestoor ✓", "HIERDIE WEEK", "Kies eers 'n bui!", "Voeg 'n nota by en stoor", listOf("Ma", "Di", "Wo", "Do", "Vr", "Sa", "So"), isRtl = false),
    "sq" to Str("Humori Im", "SOT", "Nuk është vendosur", "Zgjidh humorin tënd", "SI NDIHESH?", "SHËNIM", "Çfarë ndodhi sot?", "Ruaj", "Ruajtur ✓", "KËTË JAVË", "Zgjidh një humor fillimisht!", "Shto një shënim dhe ruaj", listOf("Hë", "Ma", "Më", "En", "Pr", "Sh", "Di"), isRtl = false),
    "am" to Str("ስሜቴ", "ዛሬ", "አልተቀናጀም", "ስሜትህን ከታች ምረጥ", "እንዴት ትሰማለህ?", "ማስታወሻ", "ዛሬ ምን ሆነ?", "አስቀምጥ", "ተቀምጧል ✓", "ይህ ሳምንት", "መጀመሪያ ስሜት ምረጥ!", "ማስታወሻ ጨምር እና አስቀምጥ", listOf("ሰኞ", "ማክ", "ረቡ", "ሐሙ", "አርብ", "ቅዳ", "እሁ"), isRtl = false),
    "ar" to Str("مزاجي", "اليوم", "غير محدد", "اختر مزاجك أدناه", "كيف تشعر؟", "ملاحظة", "ماذا حدث اليوم؟", "حفظ", "تم الحفظ ✓", "هذا الأسبوع", "يرجى اختيار المزاج أولاً!", "أضف ملاحظة واحفظ", listOf("إث", "ثل", "أر", "خم", "جم", "سب", "أح"), isRtl = true),
    "hy" to Str("Իմ տրամադրությունը", "ԱՅՍՕՐ", "Չի նշված", "Ընտրիր տրամադրությունը", "ԻՆՉ ԵՍ ԶԳՈՒՄ?", "ՆՇՈՒՄ", "Ինչ եղավ այսօր?", "Պահել", "Պահված ✓", "ԱՅՍ ՇԱԲԱԹ", "Նախ ընտրիր տրամադրություն!", "Ավելացրու նշում և պահիր", listOf("Երկ", "Երք", "Չրք", "Հնգ", "Ուրբ", "Շբթ", "Կիր"), isRtl = false),
    "az" to Str("Əhvalım", "BU GÜN", "Seçilməyib", "Əhvalını seç", "NECƏ HİSS EDİRSƏN?", "QEYD", "Bu gün nə baş verdi?", "Saxla", "Saxlanıldı ✓", "BU HƏFTƏ", "Əvvəlcə əhval seçin!", "Qeyd əlavə et və saxla", listOf("Ba", "Çə", "Çr", "Ca", "Cü", "Şə", "Bz"), isRtl = false),
    "eu" to Str("Nire alda", "GAUR", "Ezarri gabe", "Aukeratu zure alda", "NOLA SENTITZEN ZARA?", "OHARRA", "Zer gertatu da gaur?", "Gorde", "Gordeta ✓", "ASTE HONETAN", "Aukeratu alda bat lehenik!", "Gehitu ohar bat eta gorde", listOf("Al", "Ar", "Az", "Og", "Or", "La", "Ig"), isRtl = false),
    "be" to Str("Мой настрой", "СЁННЯ", "Не ўказана", "Выберы настрой ніжэй", "ЯК ТЫ СЯБЕ АДЧУВАЕШ?", "НАТАТКА", "Што здарылася сёння?", "Захаваць", "Захавана ✓", "ГЭТЫ ТЫДЗЕНЬ", "Спачатку выбяры настрой!", "Дадай нататку і захавай", listOf("Пн", "Аў", "Ср", "Чц", "Пт", "Сб", "Нд"), isRtl = false),
    "bn" to Str("আমার মেজাজ", "আজ", "সেট করা হয়নি", "নিচে থেকে মেজাজ বেছে নিন", "আপনি কেমন অনুভব করছেন?", "নোট", "আজ কী হয়েছিল?", "সংরক্ষণ", "সংরক্ষিত ✓", "এই সপ্তাহ", "প্রথমে একটি মেজাজ বেছে নিন!", "একটি নোট যোগ করুন এবং সংরক্ষণ করুন", listOf("সোম", "মঙ", "বুধ", "বৃহ", "শুক", "শনি", "রবি"), isRtl = false),
    "bs" to Str("Moje raspoloženje", "DANAS", "Nije postavljeno", "Odaberite raspoloženje", "KAKO SE OSJEĆATE?", "BILJEŠKA", "Šta se danas dogodilo?", "Spremi", "Spremljeno ✓", "OVAJ TJEDAN", "Prvo odaberite raspoloženje!", "Dodaj bilješku i spremi", listOf("Po", "Ut", "Sr", "Če", "Pe", "Su", "Ne"), isRtl = false),
    "bg" to Str("Моето настроение", "ДНЕС", "Не е зададено", "Избери настроение", "КАК СЕ ЧУВСТВАШ?", "БЕЛЕЖКА", "Какво се случи днес?", "Запази", "Запазено ✓", "ТАЗИ СЕДМИЦА", "Първо избери настроение!", "Добави бележка и запази", listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Нд"), isRtl = false),
    "ca" to Str("El meu estat d'ànim", "AVUI", "No establert", "Tria el teu estat", "COM ET SENTS?", "NOTA", "Què ha passat avui?", "Desa", "Desat ✓", "AQUESTA SETMANA", "Tria primer un estat!", "Afegeix una nota i desa", listOf("Dl", "Dt", "Dc", "Dj", "Dv", "Ds", "Dg"), isRtl = false),
    "zh" to Str("我的心情", "今天", "未设置", "在下方选择心情", "你感觉如何?", "备注", "今天发生了什么?", "保存", "已保存 ✓", "本周", "请先选择心情!", "添加备注并保存", listOf("周一", "周二", "周三", "周四", "周五", "周六", "周日"), isRtl = false),
    "zh-TW" to Str("我的心情", "今天", "未設定", "在下方選擇心情", "你感覺如何?", "備註", "今天發生了什麼?", "儲存", "已儲存 ✓", "本週", "請先選擇心情!", "新增備註並儲存", listOf("週一", "週二", "週三", "週四", "週五", "週六", "週日"), isRtl = false),
    "hr" to Str("Moje raspoloženje", "DANAS", "Nije postavljeno", "Odaberi raspoloženje", "KAKO SE OSJEĆAŠ?", "BILJEŠKA", "Što se danas dogodilo?", "Spremi", "Spremljeno ✓", "OVAJ TJEDAN", "Najprije odaberi raspoloženje!", "Dodaj bilješku i spremi", listOf("Pon", "Uto", "Sri", "Čet", "Pet", "Sub", "Ned"), isRtl = false),
    "cs" to Str("Moje nálada", "DNES", "Nenastaveno", "Vyber svou náladu", "JAK SE CÍTÍŠ?", "POZNÁMKA", "Co se dnes stalo?", "Uložit", "Uloženo ✓", "TENTO TÝDEN", "Nejprve vyber náladu!", "Přidej poznámku a ulož", listOf("Po", "Út", "St", "Čt", "Pá", "So", "Ne"), isRtl = false),
    "da" to Str("Mit humør", "I DAG", "Ikke angivet", "Vælg dit humør", "HVORDAN HAR DU DET?", "NOTE", "Hvad skete der i dag?", "Gem", "Gemt ✓", "DENNE UGE", "Vælg først et humør!", "Tilføj en note og gem", listOf("Ma", "Ti", "On", "To", "Fr", "Lø", "Sø"), isRtl = false),
    "nl" to Str("Mijn stemming", "VANDAAG", "Niet ingesteld", "Kies je stemming", "HOE VOEL JE JE?", "NOTITIE", "Wat is er vandaag gebeurd?", "Opslaan", "Opgeslagen ✓", "DEZE WEEK", "Kies eerst een stemming!", "Voeg een notitie toe en sla op", listOf("Ma", "Di", "Wo", "Do", "Vr", "Za", "Zo"), isRtl = false),
    "en" to Str("My Mood", "TODAY", "Not set", "Pick your mood below", "HOW ARE YOU FEELING?", "NOTE", "What happened today?", "Save", "Saved ✓", "THIS WEEK", "Please pick a mood first!", "Add a note and save", listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"), isRtl = false),
    "et" to Str("Minu tuju", "TÄNA", "Pole määratud", "Vali oma tuju", "KUIDAS ENNAST TUNNED?", "MÄRKUS", "Mis täna juhtus?", "Salvesta", "Salvestatud ✓", "SEL NÄDALAL", "Vali kõigepealt tuju!", "Lisa märkus ja salvesta", listOf("E", "T", "K", "N", "R", "L", "P"), isRtl = false),
    "fil" to Str("Aking Mood", "NGAYON", "Hindi nakatakda", "Piliin ang iyong mood", "PAANO KA NAKAKARAMDAM?", "TALA", "Ano ang nangyari ngayon?", "I-save", "Nai-save ✓", "SA LINGGONG ITO", "Mangyaring pumili muna ng mood!", "Magdagdag ng tala at i-save", listOf("Lun", "Mar", "Miy", "Huw", "Biy", "Sab", "Lin"), isRtl = false),
    "fi" to Str("Mielialani", "TÄNÄÄN", "Ei asetettu", "Valitse mielialasi", "MILTÄ SINUSTA TUNTUU?", "HUOMIO", "Mitä tänään tapahtui?", "Tallenna", "Tallennettu ✓", "TÄLLÄ VIIKOLLA", "Valitse ensin mieliala!", "Lisää huomio ja tallenna", listOf("Ma", "Ti", "Ke", "To", "Pe", "La", "Su"), isRtl = false),
    "fr" to Str("Mon humeur", "AUJOURD'HUI", "Non défini", "Choisissez votre humeur", "COMMENT VOUS SENTEZ-VOUS?", "NOTE", "Que s'est-il passé aujourd'hui?", "Enregistrer", "Enregistré ✓", "CETTE SEMAINE", "Choisissez d'abord une humeur!", "Ajoutez une note et enregistrez", listOf("Lun", "Mar", "Mer", "Jeu", "Ven", "Sam", "Dim"), isRtl = false),
    "gl" to Str("O meu humor", "HOXE", "Non establecido", "Escolle o teu humor", "COMO TE SENTES?", "NOTA", "Que pasou hoxe?", "Gardar", "Gardado ✓", "ESTA SEMANA", "Primeiro escolle un humor!", "Engade unha nota e garda", listOf("Lun", "Mar", "Mér", "Xov", "Ven", "Sáb", "Dom"), isRtl = false),
    "ka" to Str("ჩემი განწყობა", "დღეს", "არ არის დაყენებული", "აირჩიე განწყობა", "როგორ გრძნობ თავს?", "შენიშვნა", "დღეს რა მოხდა?", "შენახვა", "შენახულია ✓", "ამ კვირაში", "ჯერ აირჩიე განწყობა!", "დაამატე შენიშვნა და შეინახე", listOf("ორშ", "სამ", "ოთხ", "ხუთ", "პარ", "შაბ", "კვი"), isRtl = false),
    "de" to Str("Meine Stimmung", "HEUTE", "Nicht gesetzt", "Wähle deine Stimmung", "WIE FÜHLST DU DICH?", "NOTIZ", "Was ist heute passiert?", "Speichern", "Gespeichert ✓", "DIESE WOCHE", "Bitte wähle zuerst eine Stimmung!", "Notiz hinzufügen und speichern", listOf("Mo", "Di", "Mi", "Do", "Fr", "Sa", "So"), isRtl = false),
    "el" to Str("Η διάθεσή μου", "ΣΉΜΕΡΑ", "Δεν έχει οριστεί", "Επίλεξε τη διάθεσή σου", "ΠΩΣ ΑΙΣΘΑΝΕΣΑΙ;", "ΣΗΜΕΊΩΣΗ", "Τι έγινε σήμερα;", "Αποθήκευση", "Αποθηκεύτηκε ✓", "ΑΥΤΉ ΤΗΝ ΕΒΔΟΜΆΔΑ", "Επίλεξε πρώτα μια διάθεση!", "Πρόσθεσε σημείωση και αποθήκευσε", listOf("Δευ", "Τρί", "Τετ", "Πέμ", "Παρ", "Σάβ", "Κυρ"), isRtl = false),
    "gu" to Str("મારો મૂડ", "આજ", "સેટ નથી", "નીચેથી તમારો મૂડ પસંદ કરો", "તમે કેવું અનુભવો છો?", "નોંધ", "આજે શું થયું?", "સાચવો", "સાચવ્યું ✓", "આ સપ્તાહ", "પહેલા મૂડ પસંદ કરો!", "નોંધ ઉમેરો અને સાચવો", listOf("સોમ", "મંગ", "બુધ", "ગુરુ", "શુક", "શનિ", "રવિ"), isRtl = false),
    "iw" to Str("מצב הרוח שלי", "היום", "לא נבחר", "בחר מצב רוח", "איך אתה מרגיש?", "הערה", "מה קרה היום?", "שמור", "נשמר ✓", "השבוע", "בחר מצב רוח תחילה!", "הוסף הערה ושמור", listOf("ב׳", "ג׳", "ד׳", "ה׳", "ו׳", "ש׳", "א׳"), isRtl = true),
    "hi" to Str("मेरा मूड", "आज", "सेट नहीं है", "नीचे से मूड चुनें", "आप कैसा महसूस कर रहे हैं?", "नोट", "आज क्या हुआ?", "सहेजें", "सहेजा गया ✓", "इस सप्ताह", "पहले मूड चुनें!", "नोट जोड़ें और सहेजें", listOf("सोम", "मंगल", "बुध", "गुरु", "शुक्र", "शनि", "रवि"), isRtl = false),
    "hu" to Str("A hangulatom", "MA", "Nincs beállítva", "Válaszd ki a hangulatodat", "HOGY ÉRZED MAGAD?", "MEGJEGYZÉS", "Mi történt ma?", "Mentés", "Mentve ✓", "EZEN A HÉTEN", "Először válassz hangulatot!", "Adj meg egy megjegyzést és mentsd", listOf("H", "K", "Sze", "Cs", "P", "Szo", "V"), isRtl = false),
    "is" to Str("Líðan mín", "Í DAG", "Ekki stillt", "Veldu líðan þína", "HVERNIG LÍÐUR ÞÉR?", "ATHUGASEMD", "Hvað gerðist í dag?", "Vista", "Vistað ✓", "ÞESSA VIKU", "Veldu líðan fyrst!", "Bætu við athugasemd og vistaðu", listOf("Mán", "Þri", "Mið", "Fim", "Fös", "Lau", "Sun"), isRtl = false),
    "id" to Str("Suasana Hatiku", "HARI INI", "Belum diatur", "Pilih suasana hatimu", "BAGAIMANA PERASAANMU?", "CATATAN", "Apa yang terjadi hari ini?", "Simpan", "Tersimpan ✓", "MINGGU INI", "Pilih suasana hati terlebih dahulu!", "Tambahkan catatan dan simpan", listOf("Sen", "Sel", "Rab", "Kam", "Jum", "Sab", "Min"), isRtl = false),
    "it" to Str("Il mio umore", "OGGI", "Non impostato", "Scegli il tuo umore", "COME TI SENTI?", "NOTA", "Cosa è successo oggi?", "Salva", "Salvato ✓", "QUESTA SETTIMANA", "Scegli prima un umore!", "Aggiungi una nota e salva", listOf("Lun", "Mar", "Mer", "Gio", "Ven", "Sab", "Dom"), isRtl = false),
    "ja" to Str("今日の気分", "今日", "未設定", "気分を選んでください", "今どんな気分ですか?", "メモ", "今日何がありましたか?", "保存", "保存済み ✓", "今週", "まず気分を選んでください!", "メモを追加して保存", listOf("月", "火", "水", "木", "金", "土", "日"), isRtl = false),
    "kn" to Str("ನನ್ನ ಮನಸ್ಥಿತಿ", "ಇಂದು", "ಹೊಂದಿಸಲಾಗಿಲ್ಲ", "ಕೆಳಗಿನಿಂದ ಮನಸ್ಥಿತಿ ಆರಿಸಿ", "ನೀವು ಹೇಗೆ ಭಾವಿಸುತ್ತಿದ್ದೀರಿ?", "ಟಿಪ್ಪಣಿ", "ಇಂದು ಏನಾಯಿತು?", "ಉಳಿಸಿ", "ಉಳಿಸಲಾಗಿದೆ ✓", "ಈ ವಾರ", "ಮೊದಲು ಮನಸ್ಥಿತಿ ಆರಿಸಿ!", "ಟಿಪ್ಪಣಿ ಸೇರಿಸಿ ಮತ್ತು ಉಳಿಸಿ", listOf("ಸೋಮ", "ಮಂಗ", "ಬುಧ", "ಗುರು", "ಶುಕ್ರ", "ಶನಿ", "ರವಿ"), isRtl = false),
    "kk" to Str("Менің көңіл-күйім", "БҮГІН", "Орнатылмаған", "Көңіл-күйіңді таңда", "ҚАЛАЙСЫҢ?", "ЖАЗБА", "Бүгін не болды?", "Сақтау", "Сақталды ✓", "БҰЛ АПТАДА", "Алдымен көңіл-күй таңда!", "Жазба қос және сақта", listOf("Дс", "Сс", "Ср", "Бс", "Жм", "Сб", "Жк"), isRtl = false),
    "km" to Str("អារម្មណ៍របស់ខ្ញុំ", "ថ្ងៃនេះ", "មិនទាន់កំណត់", "ជ្រើសរើសអារម្មណ៍", "តើអ្នកមានអារម្មណ៍យ៉ាងណា?", "កំណត់ចំណាំ", "តើមានអ្វីកើតឡើងថ្ងៃនេះ?", "រក្សាទុក", "រក្សាទុករួច ✓", "សប្តាហ៍នេះ", "សូមជ្រើសរើសអារម្មណ៍ជាមុន!", "បន្ថែមកំណត់ចំណាំ", listOf("ច", "អ", "ព", "ព្រ", "សុ", "ស", "អា"), isRtl = false),
    "ko" to Str("내 기분", "오늘", "설정 안 됨", "아래에서 기분을 선택하세요", "기분이 어떠세요?", "메모", "오늘 무슨 일이 있었나요?", "저장", "저장됨 ✓", "이번 주", "먼저 기분을 선택해 주세요!", "메모를 추가하고 저장하세요", listOf("월", "화", "수", "목", "금", "토", "일"), isRtl = false),
    "ky" to Str("Менин маанайым", "БҮГҮН", "Орнотулган жок", "Маанайыңды танда", "КАНДАЙ СЕЗЕСИҢ?", "ЖАЗУУ", "Бүгүн эмне болду?", "Сактоо", "Сакталды ✓", "БУ ЖУМАДА", "Алгач маанай тандаңыз!", "Жазуу кош жана сакта", listOf("Дш", "Шш", "Шр", "Бш", "Жм", "Иш", "Жк"), isRtl = false),
    "lo" to Str("ອາລົມຂອງຂ້ອຍ", "ມື້ນີ້", "ຍັງບໍ່ໄດ້ຕັ້ງ", "ເລືອກອາລົມຂ້າງລຸ່ມ", "ເຈົ້າຮູ້ສຶກແນວໃດ?", "ບັນທຶກ", "ມື້ນີ້ເກີດຫຍັງຂຶ້ນ?", "ບັນທຶກ", "ບັນທຶກແລ້ວ ✓", "ອາທິດນີ້", "ກະລຸນາເລືອກອາລົມກ່ອນ!", "ເພີ່ມບັນທຶກແລ້ວບັນທຶກ", listOf("ຈ", "ອ", "ພ", "ພຫ", "ສ", "ເສ", "ອາ"), isRtl = false),
    "lv" to Str("Mans garastāvoklis", "ŠODIEN", "Nav iestatīts", "Izvēlies savu garastāvokli", "KĀ TU JŪTIES?", "PIEZĪME", "Kas šodien notika?", "Saglabāt", "Saglabāts ✓", "ŠĪ NEDĒĻA", "Vispirms izvēlies garastāvokli!", "Pievieno piezīmi un saglabā", listOf("P", "O", "T", "C", "Pk", "S", "Sv"), isRtl = false),
    "lt" to Str("Mano nuotaika", "ŠIANDIEN", "Nenustatyta", "Pasirink savo nuotaiką", "KAIP JAUTIESI?", "PASTABA", "Kas šiandien nutiko?", "Išsaugoti", "Išsaugota ✓", "ŠIĄ SAVAITĘ", "Pirmiausia pasirink nuotaiką!", "Pridėk pastabą ir išsaugok", listOf("Pr", "An", "Tr", "Kt", "Pn", "Šš", "Sk"), isRtl = false),
    "mk" to Str("Моето расположение", "ДЕНЕС", "Не е поставено", "Избери расположение", "КАК СЕ ЧУВСТВУВАШ?", "БЕЛЕШКА", "Што се случи денес?", "Зачувај", "Зачувано ✓", "ОВАА НЕДЕЛА", "Прво избери расположение!", "Додај белешка и зачувај", listOf("Пон", "Вто", "Сре", "Чет", "Пет", "Саб", "Нед"), isRtl = false),
    "ms" to Str("Mood Saya", "HARI INI", "Belum ditetapkan", "Pilih mood anda", "BAGAIMANA PERASAAN ANDA?", "NOTA", "Apa yang berlaku hari ini?", "Simpan", "Disimpan ✓", "MINGGU INI", "Sila pilih mood dahulu!", "Tambah nota dan simpan", listOf("Isn", "Sel", "Rab", "Kha", "Jum", "Sab", "Ahd"), isRtl = false),
    "ml" to Str("എന്റെ മാനസികാവസ്ഥ", "ഇന്ന്", "സജ്ജീകരിച്ചിട്ടില്ല", "താഴെ നിന്ന് മാനസികാവസ്ഥ തിരഞ്ഞെടുക്കുക", "നിങ്ങൾക്ക് എങ്ങനെ തോന്നുന്നു?", "കുറിപ്പ്", "ഇന്ന് എന്ത് സംഭവിച്ചു?", "സേവ് ചെയ്യുക", "സേവ് ചെയ്തു ✓", "ഈ ആഴ്ച", "ആദ്യം മാനസികാവസ്ഥ തിരഞ്ഞെടുക്കുക!", "കുറിപ്പ് ചേർക്കുക", listOf("തി", "ചൊ", "ബു", "വ്യ", "വെ", "ശ", "ഞ"), isRtl = false),
    "mr" to Str("माझा मूड", "आज", "सेट नाही", "खाली मूड निवडा", "तुम्हाला कसे वाटत आहे?", "नोट", "आज काय झाले?", "जतन करा", "जतन केले ✓", "या आठवड्यात", "आधी मूड निवडा!", "नोट जोडा आणि जतन करा", listOf("सोम", "मंगळ", "बुध", "गुरु", "शुक्र", "शनि", "रवि"), isRtl = false),
    "mn" to Str("Миний сэтгэл санаа", "ӨНӨӨДӨР", "Тохируулаагүй", "Сэтгэл санаагаа сонго", "ТА ЯАЖ БАЙНА?", "ТЭМДЭГЛЭЛ", "Өнөөдөр юу болсон бэ?", "Хадгалах", "Хадгалагдсан ✓", "ЭНЭ ДОЛОО ХОНОГ", "Эхлээд сэтгэл санаа сонгоно уу!", "Тэмдэглэл нэмж хадгална уу", listOf("Да", "Мя", "Лх", "Пү", "Ба", "Бя", "Ня"), isRtl = false),
    "my" to Str("ကျွန်ုပ်၏ ခံစားချက်", "ယနေ့", "မသတ်မှတ်ရသေး", "အောက်မှ ခံစားချက်ရွေးပါ", "သင်မည်သို့ ခံစားနေသနည်း?", "မှတ်စု", "ယနေ့ ဘာဖြစ်ခဲ့သနည်း?", "သိမ်းဆည်းမည်", "သိမ်းဆည်းပြီး ✓", "ဤသီတင်းပတ်", "ဦးစွာ ခံစားချက်ရွေးပါ!", "မှတ်စုထည့်ပြီး သိမ်းဆည်းပါ", listOf("တ", "အ", "ဗ", "ဗြ", "သ", "စ", "တ"), isRtl = false),
    "ne" to Str("मेरो मूड", "आज", "सेट गरिएको छैन", "तलबाट मूड छान्नुस्", "तपाईंलाई कस्तो लाग्छ?", "नोट", "आज के भयो?", "सेभ गर्नुस्", "सेभ भयो ✓", "यो हप्ता", "पहिले मूड छान्नुस्!", "नोट थप्नुस् र सेभ गर्नुस्", listOf("सोम", "मंगल", "बुध", "बिही", "शुक्र", "शनि", "आइत"), isRtl = false),
    "nb" to Str("Min stemning", "I DAG", "Ikke angitt", "Velg stemningen din", "HVORDAN HAR DU DET?", "NOTAT", "Hva skjedde i dag?", "Lagre", "Lagret ✓", "DENNE UKEN", "Velg en stemning først!", "Legg til notat og lagre", listOf("Ma", "Ti", "On", "To", "Fr", "Lø", "Sø"), isRtl = false),
    "or" to Str("ମୋ ମନୋଭାବ", "ଆଜି", "ସ୍ଥାପିତ ହୋଇନାହିଁ", "ନିମ୍ନରୁ ମନୋଭାବ ବାଛ", "ଆପଣ କେମିତି ଅନୁଭବ କରୁଛନ୍ତି?", "ଟିପ୍ପଣୀ", "ଆଜି କଣ ହୋଇଥିଲା?", "ସଞ୍ଚୟ କର", "ସଞ୍ଚୟ ହୋଇଛି ✓", "ଏହି ସପ୍ତାହ", "ପ୍ରଥମେ ମନୋଭାବ ବାଛ!", "ଟିପ୍ପଣୀ ଯୋଡ ଏବଂ ସଞ୍ଚୟ କର", listOf("ସୋମ", "ମଙ୍ଗ", "ବୁଧ", "ଗୁରୁ", "ଶୁକ୍ର", "ଶନି", "ରବି"), isRtl = false),
    "fa" to Str("حال و هوای من", "امروز", "تنظیم نشده", "حال خود را انتخاب کنید", "چطور احساس می‌کنید?", "یادداشت", "امروز چه اتفاقی افتاد?", "ذخیره", "ذخیره شد ✓", "این هفته", "لطفاً ابتدا حالی را انتخاب کنید!", "یادداشت اضافه کنید", listOf("ش", "ی", "د", "س", "چ", "پ", "ج"), isRtl = true),
    "pl" to Str("Mój nastrój", "DZIŚ", "Nie ustawiono", "Wybierz swój nastrój", "JAK SIĘ CZUJESZ?", "NOTATKA", "Co się dziś wydarzyło?", "Zapisz", "Zapisano ✓", "W TYM TYGODNIU", "Najpierw wybierz nastrój!", "Dodaj notatkę i zapisz", listOf("Pon", "Wto", "Śro", "Czw", "Pią", "Sob", "Nie"), isRtl = false),
    "pt" to Str("Meu humor", "HOJE", "Não definido", "Escolha seu humor", "COMO VOCÊ ESTÁ SE SENTINDO?", "NOTA", "O que aconteceu hoje?", "Salvar", "Salvo ✓", "ESTA SEMANA", "Por favor, escolha um humor primeiro!", "Adicione uma nota e salve", listOf("Seg", "Ter", "Qua", "Qui", "Sex", "Sáb", "Dom"), isRtl = false),
    "pt-BR" to Str("Meu humor", "HOJE", "Não definido", "Escolha seu humor abaixo", "COMO VOCÊ ESTÁ SE SENTINDO?", "NOTA", "O que aconteceu hoje?", "Salvar", "Salvo ✓", "ESTA SEMANA", "Por favor, escolha um humor primeiro!", "Adicione uma nota e salve", listOf("Seg", "Ter", "Qua", "Qui", "Sex", "Sáb", "Dom"), isRtl = false),
    "pa" to Str("ਮੇਰਾ ਮੂਡ", "ਅੱਜ", "ਸੈੱਟ ਨਹੀਂ", "ਹੇਠਾਂ ਤੋਂ ਮੂਡ ਚੁਣੋ", "ਤੁਸੀਂ ਕਿਵੇਂ ਮਹਿਸੂਸ ਕਰ ਰਹੇ ਹੋ?", "ਨੋਟ", "ਅੱਜ ਕੀ ਹੋਇਆ?", "ਸੇਵ ਕਰੋ", "ਸੇਵ ਹੋ ਗਿਆ ✓", "ਇਸ ਹਫ਼ਤੇ", "ਪਹਿਲਾਂ ਮੂਡ ਚੁਣੋ!", "ਨੋਟ ਜੋੜੋ ਅਤੇ ਸੇਵ ਕਰੋ", listOf("ਸੋਮ", "ਮੰਗਲ", "ਬੁੱਧ", "ਵੀਰ", "ਸ਼ੁੱਕਰ", "ਸ਼ਨੀ", "ਐਤ"), isRtl = false),
    "ro" to Str("Starea mea de spirit", "AZI", "Nesetat", "Alege-ți starea de spirit", "CUM TE SIMȚI?", "NOTĂ", "Ce s-a întâmplat azi?", "Salvează", "Salvat ✓", "ACEASTĂ SĂPTĂMÂNĂ", "Alege mai întâi o stare de spirit!", "Adaugă o notă și salvează", listOf("Lun", "Mar", "Mie", "Joi", "Vin", "Sâm", "Dum"), isRtl = false),
    "ru" to Str("Моё настроение", "СЕГОДНЯ", "Не указано", "Выбери настроение ниже", "КАК ТЫ СЕБЯ ЧУВСТВУЕШЬ?", "ЗАМЕТКА", "Что произошло сегодня?", "Сохранить", "Сохранено ✓", "НЕДЕЛЯ", "Сначала выбери настроение!", "Добавь заметку и сохрани", listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"), isRtl = false),
    "sr" to Str("Моје расположење", "ДАНАС", "Није постављено", "Одабери расположење", "KAKO SE OSJEĆAŠ?", "БЕЛЕШКА", "Шта се данас догодило?", "Сачувај", "Сачувано ✓", "ОВА НЕДЕЉА", "Прво одабери расположење!", "Додај белешку и сачувај", listOf("Пон", "Уто", "Сре", "Чет", "Пет", "Суб", "Нед"), isRtl = false),
    "si" to Str("මගේ මනෝභාවය", "අද", "සකස් කර නැත", "පහළින් මනෝභාවය තෝරන්න", "ඔබට කෙසේ දැනෙනවාද?", "සටහන", "අද කුමක් සිදු විය?", "සුරකින්න", "සුරකිනා ✓", "මෙම සතිය", "කරුණාකර මුලින්ම මනෝභාවය තෝරන්න!", "සටහනක් එකතු කර සුරකින්න", listOf("සඳු", "අඟ", "බදා", "බ්‍රහ", "සිකු", "සෙන", "ඉරු"), isRtl = false),
    "sk" to Str("Moja nálada", "DNES", "Nenastavené", "Vyber svoju náladu", "AKO SA CÍTIŠ?", "POZNÁMKA", "Čo sa dnes stalo?", "Uložiť", "Uložené ✓", "TENTO TÝŽDEŇ", "Najprv vyber náladu!", "Pridaj poznámku a ulož", listOf("Po", "Ut", "St", "Š", "Pi", "So", "Ne"), isRtl = false),
    "sl" to Str("Moje razpoloženje", "DANES", "Ni nastavljeno", "Izberi razpoloženje", "KAKO SE POČUTIŠ?", "OPOMBA", "Kaj se je danes zgodilo?", "Shrani", "Shranjeno ✓", "TA TEDEN", "Najprej izberi razpoloženje!", "Dodaj opombo in shrani", listOf("Pon", "Tor", "Sre", "Čet", "Pet", "Sob", "Ned"), isRtl = false),
    "es" to Str("Mi estado de ánimo", "HOY", "No establecido", "Elige tu estado de ánimo", "¿CÓMO TE SIENTES?", "NOTA", "¿Qué pasó hoy?", "Guardar", "Guardado ✓", "ESTA SEMANA", "¡Primero elige un estado de ánimo!", "Añade una nota y guarda", listOf("Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom"), isRtl = false),
    "es-419" to Str("Mi estado de ánimo", "HOY", "No establecido", "Elige tu estado de ánimo", "¿CÓMO TE SIENTES?", "NOTA", "¿Qué pasó hoy?", "Guardar", "Guardado ✓", "ESTA SEMANA", "¡Primero elige un estado!", "Añade una nota y guarda", listOf("Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom"), isRtl = false),
    "sw" to Str("Hisia zangu", "LEO", "Haijawekwa", "Chagua hisia yako", "UNAJISIKIAJE?", "KUMBUKA", "Nini kilitokea leo?", "Hifadhi", "Imehifadhiwa ✓", "WIKI HII", "Tafadhali chagua hisia kwanza!", "Ongeza kumbuka na uhifadhi", listOf("Jtu", "Jnn", "Jtn", "Aln", "Iju", "Jmss", "Jpg"), isRtl = false),
    "sv" to Str("Mitt humör", "IDAG", "Inte angivet", "Välj ditt humör", "HUR MÅR DU?", "ANTECKNING", "Vad hände idag?", "Spara", "Sparat ✓", "DENNA VECKA", "Välj ett humör först!", "Lägg till en anteckning och spara", listOf("Mån", "Tis", "Ons", "Tor", "Fre", "Lör", "Sön"), isRtl = false),
    "ta" to Str("என் மனநிலை", "இன்று", "அமைக்கப்படவில்லை", "கீழிருந்து மனநிலை தேர்வு செய்யுங்கள்", "நீங்கள் எப்படி உணர்கிறீர்கள்?", "குறிப்பு", "இன்று என்ன நடந்தது?", "சேமி", "சேமிக்கப்பட்டது ✓", "இந்த வாரம்", "முதலில் மனநிலை தேர்வு செய்யுங்கள்!", "குறிப்பு சேர்க்கவும்", listOf("திங்", "செவ்", "புத", "வியா", "வெள்", "சனி", "ஞாயி"), isRtl = false),
    "te" to Str("నా మూడ్", "నేడు", "సెట్ చేయబడలేదు", "దిగువ నుండి మూడ్ ఎంచుకోండి", "మీకు ఎలా అనిపిస్తుంది?", "గమనిక", "నేడు ఏమి జరిగింది?", "సేవ్ చేయండి", "సేవ్ అయింది ✓", "ఈ వారం", "ముందు మూడ్ ఎంచుకోండి!", "గమనిక జోడించండి", listOf("సోమ", "మంగ", "బుధ", "గురు", "శుక్ర", "శని", "ఆది"), isRtl = false),
    "th" to Str("อารมณ์ของฉัน", "วันนี้", "ยังไม่ได้ตั้งค่า", "เลือกอารมณ์ด้านล่าง", "คุณรู้สึกอย่างไร?", "บันทึก", "วันนี้เกิดอะไรขึ้น?", "บันทึก", "บันทึกแล้ว ✓", "สัปดาห์นี้", "กรุณาเลือกอารมณ์ก่อน!", "เพิ่มบันทึกและบันทึก", listOf("จ", "อ", "พ", "พฤ", "ศ", "ส", "อา"), isRtl = false),
    "tr" to Str("Ruh halim", "BUGÜN", "Ayarlanmamış", "Ruh halini seç", "NASIL HİSSEDİYORSUN?", "NOT", "Bugün ne oldu?", "Kaydet", "Kaydedildi ✓", "BU HAFTA", "Önce bir ruh hali seç!", "Not ekle ve kaydet", listOf("Pzt", "Sal", "Çar", "Per", "Cum", "Cmt", "Paz"), isRtl = false),
    "tk" to Str("Meniň keýpim", "ŞUGÜN", "Bellenilmedik", "Keýpiňi saý", "NÄHILI DUÝÝARSYŇ?", "BELLIK", "Şugün näme boldy?", "Sakla", "Saklady ✓", "BU HEPDE", "Ilki keýip saý!", "Bellik goş we sakla", listOf("Db", "Sb", "Çr", "Pb", "An", "Şb", "Ýb"), isRtl = false),
    "uk" to Str("Мій настрій", "СЬОГОДНІ", "Не встановлено", "Обери настрій нижче", "ЯК ТИ СЕБЕ ПОЧУВАЄШ?", "НОТАТКА", "Що сталося сьогодні?", "Зберегти", "Збережено ✓", "ЦЬОГО ТИЖНЯ", "Спочатку обери настрій!", "Додай нотатку і збережи", listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Нд"), isRtl = false),
    "ur" to Str("میرا موڈ", "آج", "سیٹ نہیں", "نیچے سے موڈ منتخب کریں", "آپ کیسا محسوس کر رہے ہیں؟", "نوٹ", "آج کیا ہوا؟", "محفوظ کریں", "محفوظ ✓", "اس ہفتے", "پہلے موڈ منتخب کریں!", "نوٹ شامل کریں اور محفوظ کریں", listOf("پیر", "منگل", "بدھ", "جمعرات", "جمعہ", "ہفتہ", "اتوار"), isRtl = true),
    "ug" to Str("مېنىڭ كەيپىياتىم", "بۈگۈن", "تەڭشەلمەن", "تۆۋەندىن كەيپىيات تاللاڭ", "قانداق ھېس قىلىسىز؟", "ئەسكەرتمە", "بۈگۈن نېمە بولدى؟", "ساقلاش", "ساقلاندى ✓", "بۇ ھەپتە", "ئالدى بىلەن كەيپىيات تاللاڭ!", "ئەسكەرتمە قوشۇپ ساقلاڭ", listOf("دۈش", "سەي", "چار", "پەي", "جۈم", "شەم", "يەك"), isRtl = true),
    "uz" to Str("Mening kayfiyatim", "BUGUN", "O'rnatilmagan", "Kayfiyatingizni tanlang", "O'ZINGIZNI QANDAY HIS QILYAPSIZ?", "ESLATMA", "Bugun nima bo'ldi?", "Saqlash", "Saqlandi ✓", "BU HAFTA", "Avval kayfiyat tanlang!", "Eslatma qo'shing va saqlang", listOf("Du", "Se", "Ch", "Pa", "Ju", "Sh", "Ya"), isRtl = false),
    "vi" to Str("Tâm trạng của tôi", "HÔM NAY", "Chưa đặt", "Chọn tâm trạng của bạn", "BẠN CẢM THẤY THẾ NÀO?", "GHI CHÚ", "Hôm nay có chuyện gì xảy ra?", "Lưu", "Đã lưu ✓", "TUẦN NÀY", "Hãy chọn tâm trạng trước!", "Thêm ghi chú và lưu", listOf("T2", "T3", "T4", "T5", "T6", "T7", "CN"), isRtl = false),
    "cy" to Str("Fy hwyl", "HEDDIW", "Heb ei osod", "Dewiswch eich hwyl", "SUT YDYCH CHI'N TEIMLO?", "NODYN", "Beth ddigwyddodd heddiw?", "Cadw", "Wedi cadw ✓", "YR WYTHNOS HON", "Dewiswch hwyl yn gyntaf!", "Ychwanegu nodyn a chadw", listOf("Llun", "Maw", "Mer", "Iau", "Gwe", "Sad", "Sul"), isRtl = false),
    "zu" to Str("Umzwelo wami", "NAMUHLA", "Akukabiwi", "Khetha umzwelo wakho", "UZIZWA KANJANI?", "INOTHI", "Kwenzekeni namuhla?", "Gcina", "Kugcinwe ✓", "LELI SONTO", "Sicela ukhethe umzwelo kuqala!", "Engeza inothi futhi ugcine", listOf("Mso", "Lwes", "Lwe", "Lws", "Lwe", "Mgq", "Sonto"), isRtl = false),
)

val LANG_CODES = STRINGS.keys.toList()


class MainActivity : AppCompatActivity() {
    private lateinit var prefs: SharedPreferences
    private lateinit var str: Str
    private lateinit var langCode: String
    private var selectedMoodIndex = -1
    private lateinit var emojiView: TextView
    private lateinit var labelView: TextView
    private lateinit var noteView: TextView
    private lateinit var moodButtons: List<CardView>
    private lateinit var weekRow: LinearLayout
    private lateinit var noteInput: EditText
    private lateinit var saveBtn: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = getSharedPreferences("mood_prefs", Context.MODE_PRIVATE)
        val sys = Locale.getDefault().language
        langCode = prefs.getString("lang", if (STRINGS.containsKey(sys)) sys else "en") ?: "en"
        if (!STRINGS.containsKey(langCode)) langCode = "en"
        str = STRINGS[langCode]!!
        setContentView(buildUI())
    }

    private fun todayKey() = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    private fun buildUI(): View {
        val scroll = ScrollView(this)
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(0xFFF5F5F0.toInt())
            layoutDirection = if (str.isRtl) View.LAYOUT_DIRECTION_RTL else View.LAYOUT_DIRECTION_LTR
        }
        scroll.addView(root)
        root.addView(View(this).apply {
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 48)
        })

        // Language button
        val langBtnView = TextView(this).apply {
            text = "\uD83C\uDF10  " + (LANG_DISPLAY[langCode] ?: langCode)
            textSize = 12f; setPadding(20, 10, 20, 10)
            setTextColor(0xFF444444.toInt())
            background = android.graphics.drawable.GradientDrawable().apply {
                cornerRadius = 20f; setColor(0xFFEEEEEE.toInt())
            }
            setOnClickListener { showLanguagePicker() }
        }
        root.addView(LinearLayout(this).apply {
            gravity = Gravity.END; setPadding(24, 8, 24, 0); addView(langBtnView)
        })

        // Header
        root.addView(LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL; setPadding(32, 16, 32, 8)
            addView(TextView(context).apply {
                text = str.appTitle; textSize = 26f
                setTextColor(0xFF1A1A1A.toInt())
                typeface = android.graphics.Typeface.DEFAULT_BOLD
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            })
            addView(TextView(context).apply {
                val fmt = SimpleDateFormat(if (str.isRtl) "d/M" else "d MMM", Locale.getDefault())
                text = fmt.format(Date()); textSize = 14f
                setTextColor(0xFF888888.toInt()); gravity = Gravity.BOTTOM
            })
        })

        // Today card
        val key = todayKey()
        val savedEmoji = prefs.getString("$key:emoji", "☁️") ?: "☁️"
        val savedLabel = prefs.getString("$key:label:$langCode", str.noMood) ?: str.noMood
        val savedNote  = prefs.getString("$key:note", "") ?: ""
        emojiView = TextView(this).apply { text = savedEmoji; textSize = 40f }
        labelView = TextView(this).apply {
            text = savedLabel; textSize = 20f
            setTextColor(0xFFFFFFFF.toInt())
            typeface = android.graphics.Typeface.DEFAULT_BOLD
        }
        noteView = TextView(this).apply {
            text = if (savedNote.isEmpty()) str.pickBelow else savedNote
            textSize = 13f; setTextColor(0xFFAAAAAA.toInt())
        }
        root.addView(CardView(this).apply {
            radius = 40f; setCardBackgroundColor(0xFF1A1A1A.toInt()); cardElevation = 0f
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { setMargins(32, 16, 32, 24) }
            addView(LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL; setPadding(32, 28, 32, 28)
                layoutDirection = if (str.isRtl) View.LAYOUT_DIRECTION_RTL else View.LAYOUT_DIRECTION_LTR
                addView(TextView(context).apply {
                    text = str.today; textSize = 10f
                    setTextColor(0xFF888888.toInt()); letterSpacing = 0.1f
                })
                addView(emojiView); addView(labelView); addView(noteView)
            })
        })

        // Moods
        root.addView(sectionLabel(str.howAreYou))
        val buttons = mutableListOf<CardView>()
        val grid = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL; setPadding(16, 0, 16, 24) }
        MOODS.forEachIndexed { idx, mood ->
            val lbl = mood.labels[langCode] ?: mood.labels["en"]!!
            val lblView = TextView(this).apply {
                text = lbl; textSize = 9f
                setTextColor(0xFF888888.toInt()); gravity = Gravity.CENTER
            }
            val inner = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL; gravity = Gravity.CENTER; setPadding(4, 16, 4, 16)
                addView(TextView(context).apply { text = mood.emoji; textSize = 24f; gravity = Gravity.CENTER })
                addView(lblView)
            }
            val card = CardView(this).apply {
                radius = 28f; setCardBackgroundColor(0xFFFFFFFF.toInt()); cardElevation = 0f
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                    .apply { setMargins(6, 0, 6, 0) }
                addView(inner)
                setOnClickListener {
                    selectedMoodIndex = idx
                    buttons.forEach { b ->
                        b.setCardBackgroundColor(0xFFFFFFFF.toInt())
                        (b.getChildAt(0) as? LinearLayout)?.let { ll ->
                            (ll.getChildAt(1) as? TextView)?.setTextColor(0xFF888888.toInt())
                        }
                    }
                    setCardBackgroundColor(0xFF1A1A1A.toInt())
                    lblView.setTextColor(0xFFAAAAAA.toInt())
                    emojiView.text = mood.emoji; labelView.text = lbl; noteView.text = str.addNote
                }
            }
            buttons.add(card); grid.addView(card)
        }
        moodButtons = buttons; root.addView(grid)

        // Note
        root.addView(sectionLabel(str.noteLbl))
        noteInput = EditText(this).apply {
            hint = str.noteHint; setHintTextColor(0xFFBBBBBB.toInt())
            setTextColor(0xFF1A1A1A.toInt()); background = null; textSize = 14f
            minLines = 2; maxLines = 3; setPadding(28, 20, 28, 20)
            layoutDirection = if (str.isRtl) View.LAYOUT_DIRECTION_RTL else View.LAYOUT_DIRECTION_LTR
            gravity = if (str.isRtl) Gravity.END else Gravity.START
        }
        root.addView(CardView(this).apply {
            radius = 28f; setCardBackgroundColor(0xFFFFFFFF.toInt()); cardElevation = 0f
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { setMargins(32, 0, 32, 16) }
            addView(noteInput)
        })

        // Save button
        saveBtn = TextView(this).apply {
            text = str.saveBtn; textSize = 15f
            setTextColor(0xFFFFFFFF.toInt())
            typeface = android.graphics.Typeface.DEFAULT_BOLD; gravity = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 120)
                .apply { setMargins(32, 0, 32, 32) }
            background = android.graphics.drawable.GradientDrawable().apply {
                setColor(0xFF1A1A1A.toInt()); cornerRadius = 36f
            }
            setOnClickListener { onSave() }
        }
        root.addView(saveBtn)

        // Week
        root.addView(sectionLabel(str.weekLabel))
        weekRow = buildWeekRow(); root.addView(weekRow)

        return scroll
    }

    private fun showLanguagePicker() {
        val items = LANG_CODES.map { LANG_DISPLAY[it] ?: it }.toTypedArray()
        android.app.AlertDialog.Builder(this)
            .setTitle("Language / Язык / שפה / اللغة")
            .setItems(items) { _, which ->
                val code = LANG_CODES[which]
                if (code != langCode) {
                    langCode = code; str = STRINGS[code]!!
                    prefs.edit().putString("lang", code).apply()
                    setContentView(buildUI())
                }
            }.show()
    }

    private fun onSave() {
        if (selectedMoodIndex < 0) { Toast.makeText(this, str.toast, Toast.LENGTH_SHORT).show(); return }
        val mood = MOODS[selectedMoodIndex]
        val note = noteInput.text.toString()
        val key = todayKey()
        prefs.edit().apply {
            putString("$key:emoji", mood.emoji)
            STRINGS.keys.forEach { lang -> putString("$key:label:$lang", mood.labels[lang] ?: mood.labels["en"]!!) }
            putString("$key:note", note)
        }.apply()
        noteView.text = if (note.isEmpty()) mood.labels[langCode] else note
        val parent = weekRow.parent as? LinearLayout
        val idx = parent?.indexOfChild(weekRow) ?: -1
        weekRow = buildWeekRow(); parent?.removeViewAt(idx); parent?.addView(weekRow, idx)
        saveBtn.text = str.savedBtn
        saveBtn.postDelayed({ saveBtn.text = str.saveBtn }, 2000)
    }

    private fun buildWeekRow(): LinearLayout {
        val row = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL; setPadding(16, 0, 16, 48) }
        val cal = Calendar.getInstance()
        val dow = (cal.get(Calendar.DAY_OF_WEEK) + 5) % 7
        cal.add(Calendar.DAY_OF_YEAR, -dow)
        val fmt = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val todayKey = todayKey()
        for (i in 0..6) {
            val k = fmt.format(cal.time)
            val emoji = prefs.getString("$k:emoji", "") ?: ""
            val isToday = k == todayKey
            row.addView(CardView(this).apply {
                radius = 20f
                setCardBackgroundColor(if (isToday) 0xFFEEEEE8.toInt() else 0xFFFFFFFF.toInt())
                cardElevation = 0f
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                    .apply { setMargins(4, 0, 4, 0) }
                addView(LinearLayout(context).apply {
                    orientation = LinearLayout.VERTICAL; gravity = Gravity.CENTER; setPadding(2, 10, 2, 10)
                    addView(TextView(context).apply {
                        text = str.days.getOrElse(i) { "" }; textSize = 9f; gravity = Gravity.CENTER
                        setTextColor(if (isToday) 0xFF444444.toInt() else 0xFFAAAAAA.toInt())
                    })
                    addView(TextView(context).apply {
                        text = if (emoji.isEmpty()) "·" else emoji
                        textSize = if (emoji.isEmpty()) 18f else 16f; gravity = Gravity.CENTER
                    })
                })
            })
            cal.add(Calendar.DAY_OF_YEAR, 1)
        }
        return row
    }

    private fun sectionLabel(text: String) = TextView(this).apply {
        this.text = text; textSize = 10f; letterSpacing = 0.1f
        setTextColor(0xFF888888.toInt()); setPadding(32, 0, 32, 10)
        layoutDirection = if (str.isRtl) View.LAYOUT_DIRECTION_RTL else View.LAYOUT_DIRECTION_LTR
    }
}
