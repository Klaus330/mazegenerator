# Maze Generator


## Subject
Maze Generator
- Cercetare, Algoritmica, Grafică
- Studierea si implementarea unor algoritmi pentru generarea de labirinturi 2D sau 3D (maze, dungeon, etc), cu posibilitatea de export in diverse formate
- http://www.astrolog.org/labyrnth/algrithm.htm
- Crearea unei interfete grafice care sa asiste procesul de generare

---
## Technologies used
1.  Java FX
2.  Collections 
3.  Streams
---
## Maze Creation Algorithms

### DFS (recursive backtracking) - Claudiu
  #### Algoritmul DFS folosit pentru generarea labirintului contine urmatoarea seceventa de pasi:
        1. Primeste celula curenta ca parametru
        2. Marcheaza celula curenta ca fiind vizitata
        3. Cat timp exista celule nevizitate:
            1. Daca celula curenta are vecini nevizitati:
                1. Alege unul dintre vecini
                2. Sterge peretele dintre celula curenta si celula aleasa
                3. Adauga celula curenta in stiva?
                4. Invoca rutina recursiv pentru celula nou aleasa
            2. Altfel:
                1. Extrage o celula din stiva, daca stiva nu este goala

### Prim (versiunea random) - Claudiu
 #### Algoritmul  folosit este o versiune randomizata a algoritmului lui Prim.
    1. Incepe cu  o grila plina de  pereti
    2. Alege o celula, marcheaz-o ca fiind parte din labirint si adauga peretii celulei in lista de pereti
    3. Cat timp sunt pereti in lista:
        1. Alege un perete random din lista. Doar daca una din cele doua celule pe care acel perete le divide este vizitata, atunci:
            1. Marcheaza celula nevizitata si transforma peretele intr-un pasaj
            2. Adauga peretii celulei nou alese in lista de pereti
        2. Sterge peretele din lista


### Wilson - Cezar
    1. Alege o celula aleatorie din grila si marcheaz-o ca vizitata
    2. Alege o alta celula aleatorie pentru a fi celula curenta
    3. Cat timp exista celule nevizitate:
        1. Daca celula curenta este vizitata, atunci:
            1. Adauga toate celulele din calea temporara in labirint (le marcheaza ca vizitate)
            2. Alege o celula aleatorie nevizitata
        2. Adauga celula curenta in calea temporara
        3. Daca exista un vecin al celulei curente care nu se afla in calea temporara, atunci:
            1. Adauga celula curenta in stiva
            2. Sterge peretele dintre celula curenta si celula aleasa
            3. Vecinul devine celula curenta
        4. Altfel, daca stiva nu este goala:
            1. Extrage o celula din stiva
        5. Altfel, marcheaza celula curenta ca fiind vizitata   

### Kruskal (versiunea random) - Cezar
    1. Creaza o multime disjuncta formata doar dintr-o celula
    2. Adauga toate aceste multimi intr-o stiva
    3. Cat timp exista celule nevizitate:
        1. Extrage o celula din varful stivei si fa-o celula curenta
        2. Marcheaza celula curenta ca fiind vizitata
        3. Pentru fiecare vecin nevizitat al celulei curente: 
            1. Daca celula curenta si vecinul nu fac parte din aceeasi multime, atunci:
                1. Sterge peretele dintre celula curenta si vecinul sau
                2. Uneste cele doua multimi
        4. Amesteca elementele din stiva

### Binary Tree - Cezar & Claudiu
    1. Alege celula curenta ca fiind ultima din labirint
    2. Cat timp exista celule nevizitate:
        1. Top = vecinul de deasupra, Left = Vecinul din stanga
        2. Daca exista Top & Left
            1.  Sterge in mod aleatoriu peretele dintre celula curenta si  unul din cei doi vecini selectati
        3. Altfel, daca exista doar Top
            1.  Sterge in mod aleatoriu peretele dintre celula curenta si Top
        4. Altfel
            1.  Sterge in mod aleatoriu peretele dintre celula curenta si Left
        5. Marcheaza celula curenta ca fiind vizitata
        6. Ia celula din stanga daca exista, altfel ia ultima celula de pe randul de deasupra
---
## Maze Solving Algorithms

### DFS - Claudiu
    1. Alege prima celula ca fiind celula curenta
    2. Cat timp celula curenta nu este ultima celula din labirint
        1. Marcheaza celula curenta ca fiind vizitata
        2. Alege un vecin nevizitat. Daca exista un astfel de vecin, atunci:
            1. Adauga celula curenta in stiva
            2. Fa vecinul ales celula curenta
        3. Scoate o celula din stiva si fa-o celula curenta

### Dijkstra - Cezar
    1. Alege prima celula din tabel ca celula curenta si seteaza-i distanta cu valoarea 0
    2. Adauga celula curenta in coada
    3.  Cat timp celula curenta nu este ultima celula din labirint
        1. Marcheaza celula curenta ca fiind vizitata
        2. Atribuie celulei curente valoarea primei celule din coada
        3. Preia toti vecinii accesibili ai celulei curente
        4. Pentru fiecare vecin fa urmatoarele:
            1. Daca vecinul nu a mai fost vizitat, atunci :
                1. Seteaza-i distanta ca fiind distanta pana la celula curenta + 1
                2. Marcheaza-i parintele ca fiind celula curenta
                3. Introdu vecinul in coada
---
## Colaborators
- Todirisca Cezar Andrei
- Popa Claudiu

