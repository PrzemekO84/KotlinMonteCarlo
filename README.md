1. Wprowadzenie
   
Do wyznaczania liczby 𝜋 wybrałem metodę Monte Carlo, ponieważ podobało mi się jej założenie wyznaczania tej wartości poprzez użycie prawdopodobieństwa. Metoda Monte Carlo w kontekście wyznaczania liczby 𝜋
opiera się na losowym rozmieszczaniu punktów w kwadracie i obliczaniu stosunku punktów, które wpadają do wpisanego w ten kwadrat koła.

![image](https://github.com/user-attachments/assets/01207e89-22e2-4755-bdde-c7b384702483)

Dodatkowo w implementacji wykorzystałem wielowątkowość, aby przyspieszyć obliczenia.

2. Opis metody
   
W klasycznej wersji tej metody punkty losuje się w przedziale [-1, 1] x [-1, 1], a następnie sprawdza, czy dany punkt znajduje się wewnątrz koła o promieniu 1. W moim przypadku zdecydowałem się na inny przedział losowania współrzędnych. Punkty są losowane w zakresie [0,1] × [0,1], czyli tylko w jednej ćwiartce układu współrzędnych.

Wówczas równanie okręgu o środku w punkcie (0,0) i promieniu 1 przyjmuje postać:
x^2 + y^2 ≤ R^2

![image](https://github.com/user-attachments/assets/d103b0fb-8ca4-4b5b-ada0-2f6c41680917)

Jeśli dany punkt spełnia to równanie, oznacza to, że znajduje się wewnątrz ćwiartki koła.

Obliczenie liczby 𝜋 opiera się na stosunku punktów wewnątrz ćwiartki koła do wszystkich wygenerowanych punktów. Ponieważ symulujemy tylko jedną ćwiartkę, ostateczne przybliżenie obliczamy według wzoru:
 
Zastosowanie przedziału [0,1] × [0,1] zamiast [-1,1] × [-1,1] nie zmienia istoty metody, lecz skupia się na jednej ćwiartce układu współrzędnych.

![image](https://github.com/user-attachments/assets/bf4b2cd8-e6e3-47a2-9839-19ca0321e8d0)

Dodatkowo, w implementacji wykorzystuję wielowątkowość, co pozwala na podział obliczeń na wiele rdzeni procesora, przyspieszając proces obliczeń.

3. Implementacja algorytmu

A) Ustalamy liczbę losowanych punktów N

B) Generujemy losowe współrzędne (x, y) w przedziale [0, R] x [0, R]. 

C) Sprawdzamy, ile z tych punktów mieści się wewnątrz ćwiartki koła (czy spełniają warunek x^2 + y^2 ≤ R^2). 

D)  Obliczamy przybliżenie liczby 𝜋 π na podstawie stosunku punktów wewnątrz ćwiartki koła do wszystkich wygenerowanych punktów: 


![image](https://github.com/user-attachments/assets/bf4b2cd8-e6e3-47a2-9839-19ca0321e8d0)

​ 
E) Wersja wielowątkowa:
Aby przyspieszyć obliczenia, używam wielowątkowości, co pozwala na równoczesne losowanie punktów i sprawdzanie ich pozycji w wielu wątkach. Każdy wątek wykonuje część iteracji, a wyniki są sumowane na końcu.

4. Podsumowanie
   
A) Większa liczba iteracji zwiększa dokładność wyniku.

B) Większa liczba wątków może przyspieszyć obliczenia, ale nie wpływa na dokładność wyniku (Świetnie pokazują to wyniki gdzie program wykona się szybciej przy małej liczbie iteracji w wersji bez wątków natomiast przy bardzo dużej ilości iteracji program wykona się znacznie szybciej podczas użycia metody wielowątkowej).

 - Mała liczba iteracji:

  ![image](https://github.com/user-attachments/assets/69cdc6a5-006f-4afa-a5fb-bfaad8574404)

 
 - Duża liczba iteracji:

   ![image](https://github.com/user-attachments/assets/fa9208c1-87db-46fa-8fdb-083574c8eaa5)

   
C) Użycie ćwiartki koła (zamiast pełnego okręgu) nie zmienia metody.
