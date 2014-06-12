<%--
  Created by IntelliJ IDEA.
  User: maciek
  Date: 12.06.14
  Time: 21:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, user-scalable=no" />
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <title>
        Szybka Pożyczka przez Internet - Vivus.pl
    </title>
    <script type="text/javascript" src="/resources/assets/js/lib/require.js"></script>
    <script type="text/javascript" src="/resources/assets/js/lib/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="/resources/assets/bootstrap/js/bootstrap.min.js"></script>
    <link href="/resources/assets/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body>
<div id="wrapper">
    <header>
        <img src="logo.png" class="logo-img" />
        <div id="login">
            <input type="text" name="email" placeholder="adres email" required class="login-field" />
            <input type="text" name="password" placeholder="hasło" required class="login-field" />
            <a href="#">
                <div class="login-button">
                    wejdź
                </div>
            </a>
        </div>
        <nav>
            <ul id="top-menu">
                <a href="index.html">
                    <li>
                        <img src="home.png" />
                    </li>
                </a>
                <a href="konto.html">
                    <li class="nav-current-page">
                        Konto
                    </li>
                </a>
            </ul>
        </nav>
    </header>
    <div id="container-white">
        <div id="register-form">

            <h1>
                Edytuj dane:
            </h1>

            <p>

                <label class="register-label">Imię</label>
                <input type="text" name="imie" placeholder="Imię" required class="register-data" />
                <br />
                <label class="register-label">Nazwisko</label>
                <input type="text" name="nazwisko" placeholder="Nazwisko" required class="register-data" />
                <br />
                <label class="register-label">PESEL</label>
                <input type="text" name="pesel" placeholder="Pesel" required class="register-data" />
                <br />
                <label class="register-label">Seria i nr dowodu osobistego</label>
                <input type="text" name="dowod" placeholder="Seria i nr dowodu osobistego" required class="register-data" />
                <br />
                <label class="register-label">Telefon komórkowy</label>
                <input type="text" name="tel" placeholder="Telefon Komórkowy" required class="register-data" />
                <br />
                <label class="register-label">Adres E-mail</label>
                <input type="text" name="email" placeholder="Adres E-mail" required class="register-data" />
                <br />
                <label class="register-label">Hasło</label>
                <input type="text" name="haslo" placeholder="Hasło" required class="register-data" />
                <br />
                <label class="register-label">Wprowadź hasło ponownie</label>
                <input type="text" name="haslo2" placeholder="Hasło" required class="register-data" />
                <br />
                <label class="register-label">Adres korespondencyjny</label>
                <input type="text" name="adres" placeholder="Adres korespondencyjny" required class="register-data" />
                <br />
                <label class="register-label">Ulica nr domu/nr mieszkania</label>
                <input type="text" name="ulica" placeholder="Ulica nr domu/nr mieszkania" required class="register-data" />
                <br />
                <label class="register-label">Kod pocztowy</label>
                <input type="text" name="kod" placeholder="Kod pocztowy" required class="register-data" />
                <br />
                <label class="register-label">Miejscowość</label>
                <input type="text" name="miejscowosc" placeholder="Miejscowość" required class="register-data" />

            <p class="zgody">
            <h1>
                Zaznacz wszystkie zgody
            </h1>
            <p>
                <input id="accept" type="checkbox" name="accept">
                Oświadczam, że zapoznałem/am się z treścią formularza informacyjnego
            </p>
            <p>
                <input id="accept" type="checkbox" name="accept">
                Oświadczam, że zapoznałem/am się i akceptuję warunki ochrony danych osobowych oraz akceptuję postanowienia Umowy i Regulaminu świadczenia Usług Drogą Elektroniczną
            </p>
            <p>
                <input id="accept" type="checkbox" name="accept">
                W związku ze złożeniem przeze mnie wniosku o pożyczkę udzielam pełnomocnictwa do wystąpienia w moim imieniu do Biura Informacji Kredytowej S.A. z prośbą o udostępnienie informacji
            </p>
            <p>
                <input id="accept" type="checkbox" name="accept">
                Wyrażam zgodę na przesyłanie przez VIVUS FINANCE i INTERSALE SERVICES LIMITED informacji handlowych z wykorzystaniem środków komunikacji elektronicznej. Wyrażam zgodę na przetwarzanie moich danych osobowych przez VIVUS FINANCE oraz przez podmioty należące do grupy kapitałowej, w której skład wchodzi VIVUS FINANCE w celu marketingu produktów tych podmiotów oraz podmiotów trzecich (współpracujących z VIVUS FINANCE i INTERSALE SERVICES LIMITED)
            </p>

            <div id="register-button">Aktualizuj dane</div>

            </p>

            </p>

        </div>
        <div id="sliders-mini">
            <p class="slider-text-mini">
                Ile pieniędzy chcesz pożyczyć?
                <span class="slider-text-em-mini">100-3500 zł</span>
            </p>
            <input type="range"  min="100" max="3500" value="1500"  step="50"  onchange="showValue1(this.value)" name="range1" class="slider-bars mini" />
            <span id="range1">1500</span>
            <script type="text/javascript">
                function showValue1(newValue1)
                {
                    document.getElementById("range1").innerHTML=newValue1;
                }
            </script>
            <p class="slider-text-mini">
                Na ile dni?
                <span class="slider-text-em-mini">1-30 dni</span>
            </p>
            <input type="range"  min="1" max="30" value="30"  step="1"  onchange="showValue2(this.value)" name="range2" class="slider-bars mini" />
            <span id="range2">30</span>
            <script type="text/javascript">
                function showValue2(newValue2)
                {
                    document.getElementById("range2").innerHTML=newValue2;
                }
            </script>
        </div>
        <div id="slider-details-mini">
            <h4>Termin spłaty [value]</h4>
            Pożyczka:	<span class="slider-values">[value]</span>
            <br />
            Prowizja:	<span class="slider-values">[value]</span>
            <br />
            Razem:	<span class="slider-values">[value]</span>
            <br />
            RRSO:	<span class="slider-values">[value]</span>

            <h4>Przedłużenie terminu spłaty:</h4>

            7 dni: 	<span class="slider-values">[value]</span>
            <br />
            14 dni:	<span class="slider-values">[value]</span>
            <br />
            30 dni:	<span class="slider-values">[value]</span>
            <br /><br />
            <center>
                <a href="#">
                    Formularz informacyjny
                </a>
            </center>
        </div>
    </div>
</div>
<footer>

    <div class="legalities">
        <p class="whitespace"></p>
        <p>Vivus Finance Spółka z ograniczoną odpowiedzialnością z siedzibą w Warszawie (02-146) przy ul. 17 Stycznia 56, telefon 221 221 227. NIP: 525-253-13-20, REGON: 146101268. Sąd Rejonowy dla m.st. Warszawy XIII Wydział Gospodarczy Krajowego Rejestru Sądowego, KRS 0000418977. Kapitał zakładowy w wysokości 9 300 000. </p>

        <p>Informujemy, że działamy w oparciu o przepisy polskiego prawa określone w szczególności w Kodeksie cywilnym oraz Ustawie o kredycie konsumenckim.</p>

        <p>RRSO w przypadku pierwszej pożyczki wynosi 0%. Jednorazowa opłata rejestracyjna wynosi 0,01 PLN (gdy Klient dokonuje opłaty rejestracyjnej na konto w jednym z banków w którym Vivus Finance posiada swoje konto) lub opłata wynosi 1 PLN (gdy Klient dokonuje opłaty rejestracyjnej za pośrednictwem Systemu „BlueCash”). Reprezentatywny przykład: całkowita kwota pożyczki 2000 PLN; czas obowiązywania umowy 30 dni; oprocentowanie 0%; opłata przygotowawcza 0%; prowizja 280 PLN; całkowita kwota do zapłaty 2280 PLN; RRSO 392% (stan na dzień 05.06.2014r.)</p>

        <p>Dodatkowe koszty związane z pożyczką mogą być naliczone wyłącznie w momencie braku terminowej spłaty udzielonej pożyczki. W przypadku opóźnienia w spłacie Pożyczki, Pożyczkodawca zastrzega sobie możliwość naliczenia odsetek za zwłokę w wysokości czterokrotnej wysokości stopy kredytu lombardowego Narodowego Banku Polskiego oraz podjęcia działań mających na celu zwrot Pożyczki przez Pożyczkobiorcę. W szczególności Pożyczkodawca wezwie Pożyczkobiorcę do zapłaty telefonicznie lub wysyłając wiadomość SMS, e-mail lub pisemne wezwania do zapłaty. Pożyczkobiorca zostanie obciążony kosztami pisemnego wezwania do zapłaty drogą pocztową w następującej wysokości: 35 PLN za przesłanie pierwszego wezwania do zapłaty po upływie 30 dni kalendarzowych od upływu Terminu spłaty Pożyczki, 45 PLN za przesłanie drugiego wezwania do zapłaty, po upływie 60 dni kalendarzowych od upływu Terminu spłaty Pożyczki, 55 PLN za przesłanie trzeciego wezwania do zapłaty, po upływie 90 dni kalendarzowych od upływu Terminu spłaty Pożyczki. Kosztami dochodzenia zwrotu Pożyczki na drodze postępowania sądowego zostanie obciążony Pożyczkobiorca.</p>

        <p>W przypadku łącznego spełnienia przesłanek określonych w artykule 14 lub 16 bądź 17 ustawy z dnia 9 kwietnia 2010 r. o udostępnianiu informacji gospodarczych i wymianie danych gospodarczych (Dz.U. Nr 81, poz. 530) Pożyczkodawca ma prawo przekazać informacje o zobowiązaniach Pożyczkobiorcy do Biur Informacji Gospodarczej, co może mieć wpływ na zdolność kredytową Pożyczkobiorcy. Przekazanie danych Pożyczkobiorcy do Biur Informacji Gospodarczej jest dokonywane na podstawie pisemnej umowy o udostępnianie informacji gospodarczych zawartej przez Pożyczkodawcę z Biurem Informacji Gospodarczej oraz w oparciu o przepisy rozdziału 3 ustawy o udostępnianiu informacji gospodarczych i wymianie danych gospodarczych.</p>

        <p>Przedłużenie spłaty pożyczki nie odbywa się automatycznie i jest zależne od woli klienta. Przedłużenie terminu spłaty odbywa się po dokonaniu opłaty za przedłużenie, której wysokość zależy od kwoty pożyczki i terminu przedłużenia.<br />
            Szybka pożyczka przez Internet - Vivus.pl</p>
    </div>
</footer>
</body>
</html>
