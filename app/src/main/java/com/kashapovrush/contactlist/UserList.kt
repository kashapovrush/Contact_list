package com.kashapovrush.contactlist

import java.util.Random

object UserList {

    private var listFirstName = mutableListOf(
        "Иван",
        "Сергей",
        "Андрей",
        "Петр",
        "Геннадий",
        "Александр",
        "Алексей",
        "Федор",
        "Анатолий",
        "Стас"
    )

    private var listLastName = mutableListOf(
        "Иванов",
        "Сергеев",
        "Андреев",
        "Петров",
        "Пушкин",
        "Александров",
        "Алексеев",
        "Федоров",
        "Васильев",
        "Генадьев"
    )
    var resultList = mutableListOf<User>()
    var deleteList = mutableListOf<User>()
    var id = 0

    fun setUsers() {
        if (resultList.size == 0) {
            listFirstName.forEach { firstName ->
                listLastName.forEach { lastName ->
                    resultList.add(
                        User(
                            id++,
                            firstName,
                            lastName,
                            "+7999${Random().nextInt(9999999)}"
                        )
                    )
                }
            }
        }
    }
}