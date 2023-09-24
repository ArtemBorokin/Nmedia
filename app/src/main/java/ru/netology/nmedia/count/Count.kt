package ru.netology.nmedia.count

fun count(count: Int): String {
    val hundred = (count % 1000) / 100
    val thousand = count / 1000
    val million = count / 1000000
    val hundredThousand = (count % 1000000) / 100000

    when {
        count < 1000 ->
            return count.toString()

        count in 1000..9999 ->
            return if (count % 1000 in 0..99) {
                "$thousand K"
            } else return "$thousand.$hundred K"

        count in 10000..999999 ->
            return "$thousand K"

        else -> {
            return if (count % 1000000 in 0..99999) {
                "$million M"
            } else {
                "$million.$hundredThousand M"
            }
        }
    }
}


//При клике на like должна меняться не только картинка, но и число рядом с ней: лайкаете — увеличивается на 1, дизлайкаете — уменьшается на 1.
//При клике на share должно увеличиваться число рядом: 10 раз нажали на share — +10.
//Если количество лайков, share или просмотров перевалило за 999, должно отображаться 1K и т. д., а не 1 000. Предыдущие функции должны работать: если у поста было 999 лайков и вы нажали like,
//то должно стать 1К, если убрали лайк, то снова 999.
//Обратите внимание:

//1.1К отображается по достижении 1 100.
//После 10К сотни перестают отображаться.
//После 1M сотни тысяч отображаются в формате 1.3M.
