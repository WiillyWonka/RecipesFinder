# Пользование БД
## Необходимые файлы
* DataBase.java
* DataBaseHelper.java
* Dish.java
## Как пользоваться
В MainActivity создаём экземпляр
```java
DataBase db = DataBase.getDataBase(this);
```
Для получения списка блюд по ингредиентам вызываем:
```java
db.getDishListByIngredients(ingredients);
```
В качестве аргумента передаём String[]

На выходе получаем Dish[]

Dish, класс хранящий всё необходимое для блюда
