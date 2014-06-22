<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script type="text/javascript" src="/resources/assets/js/lib/require.js"></script>
    <script type="text/javascript" src="/resources/assets/js/lib/jquery.js"></script>
    <script type="text/javascript" src="/resources/assets/bootstrap/js/bootstrap.min.js"></script>
    <link href="/resources/assets/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resources/assets/css/style.css"  rel="stylesheet" type="text/css" />
    <title></title>
</head>
<div id="wrapper">
    <header>
        <img src="/resources/assets/img/logo.png" class="logo-img" />
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
                <a href="homepage.html">
                    <li class="nav-current-page">
                        <img src="/resources/assets/img/home.png" />
                    </li>
                </a>
                <a href="applyforloan.html">
                    <li class="apply-for-loan">
                        Weź Pożyczkę
                    </li>
                </a>
                <a href="accountservices.html">
                    <li class="account-services">
                        Operacje na koncie
                    </li>
                </a>
            </ul>
        </nav>
    </header>
    <div id="promo-img">
        <img src="/resources/assets/img/1.png" />
    </div>
    <div id="container">
        <div id="sliders">
            <p class="slider-text">
                Ile pieniędzy chcesz pożyczyć?
                <span class="slider-text-em">100-3500 zł</span>
            </p>
            <input type="range"  min="100" max="3500" value="1500"  step="50"  onchange="showValue1(this.value)" name="range1" class="slider-bars" />
            <span id="range1">1500</span>
            <script type="text/javascript">
                function showValue1(newValue1)
                {
                    document.getElementById("range1").innerHTML=newValue1;
                }
            </script>
            <p class="whitespace"></p>
            <p class="slider-text">
                Na ile dni?
                <span class="slider-text-em">1-30 dni</span>
            </p>
            <input type="range"  min="1" max="30" value="30"  step="1"  onchange="showValue2(this.value)" name="range2" class="slider-bars" />
            <span id="range2">30</span>
            <script type="text/javascript">
                function showValue2(newValue2)
                {
                    document.getElementById("range2").innerHTML=newValue2;
                }
            </script>
        </div>
        <div id="slider-details">
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
            <a href="#">
                <p class="sliders-button">
                    Weź pożyczkę
                </p>
            </a>
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
