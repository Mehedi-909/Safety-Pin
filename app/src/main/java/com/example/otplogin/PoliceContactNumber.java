package com.example.otplogin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PoliceContactNumber extends AppCompatActivity {

    ListView listView;
    SearchView mySearchView;
    TextView textView;
    private LocationRequest locationRequest;
    DatabaseReference reference;
    ArrayList<NearestPolice> policeStationLatLng = new ArrayList<>();
    //LatLng locationR;
    //Coordinate co;
    String nearestP = "";


    String dhakaPoliceStationNumbers[] = {
            "Ramana 01713373125",
            "Dhanamandi 01713373126",
            "Sahabag 01713373127",
            "NewMarket 01713373128",
            "Lalabag 01713373134",
            "Kotayali 01713373135",
            "Hajaribag 01713373136",
            "Kamarangiracar 01713373137",
            "Sutrapur 01713373143",
            "Demar 01713373144",
            "Syamapur 01713373145",
            "Jatrabari 01713373146",
            "Motijheel 01713373152",
            "Sabujabag 01713373153",
            "Khilagao 01713373154",
            "Paltan 01713373155",
            "Uttara 01713373161",
            "Airport 01713373162",
            "Turag 01713373163",
            "Uttarakhan 01713373164",
            "Daksinakhan 01713373165",
            "Gulasan 01713373171",
            "DhakaCantonment 01713373172",
            "Badda 01713373173",
            "Khilakhet 01713373174",
            "Tejagao 01713373180",
            "TejagaoIndustrialArea-01713373181",
            "Mohammadapur 01713373182",
            "Adabar 01713373183",
            "Mirapur 01713373189",
            "Pallabi 01713373190",
            "Kafrul 01713373191",
            "ShahAli 01713373192",
            "Savar 01713373327",
            "Dhamarai 01713373328",
            "Keraniganj 01713373329",
            "Nababganj 01713373330",
            "Dohar 01713373331",
            "Ashuliya 01713373332",
            "DokkhinKeraniganj 01713373333",
            "Narayanganj 01713373345",
            "Fatulla 01713373346",
            "Bandar(Narayangonj) 01713373347",
            "Shiddhirganj 01713373348",
            "Araihajar 01713373349",
            "Sonargao 01713373350",
            "Rupganj 01713373351",
            "Joydebpur 01713373363",
            "Tongi 01713373364",
            "Kaliyakair 01713373365",
            "Sripur 01713373366",
            "Kapasiya 01713373367",
            "Kaliganj 01713373368",
            "Manikganj 01713373379\n",
            "Ghior 01713373380",
            "Sibaloy 01713373381",
            "Daulatpur 01713373382",
            "Harirampur 01713373383",
            "Saturiya 01713373384",
            "Singair 01713373385",
            "Munsiganj 01713373396",
            "Tongibari 01713373397",
            "Lauhajang 01713373398",
            "Srinagar 01713373399",
            "Sirajdikhan 01713373400",
            "Gojariya 01713373401",
            "Narsingdi 01713373412",
            "Raypura 01713373413",
            "Shibpur 01713373414",
            "Belabo 01713373415",
            "Monohardi 01713373416",
            "Palash 01713373417",
            "KotwaliMayamanasinha 01713373430",
            "Muktagacha 01713373431",
            "Fulbari 01713373432",
            "Trishal 01713373433",
            "Gauripur 01713373434",
            "IswarGanj 01713373435",
            "Nandail 01713373436",
            "Fulpur 01713373437",
            "Haluyaghat 01713373438",
            "Dhobaura 01713373439",
            "Gaffargao 01713373440",
            "Valuka 01713373441",
            "Tarakandi 01713373442",
            "Tangail 01713373454",
            "Mirjapur 01713373455",
            "Nagorpur 01713373456",
            "Sakhipur 01713373457",
            "Basail 01713373458",
            "Delduar 01713373459",
            "Madhupur 01713373460",
            "Ghatail 01713373461",
            "Kalihati 01713373462",
            "Vuyapur 01713373463",
            "JamunaBridgeEast 01713373464",
            "Dhanbar 01713373465",
            "Gopalpur 01713373466",
            "Kishorgonj 01713373480",
            "Karimganj 01713373481",
            "Tarail 01713373482",
            "Hosenpur 01713373483",
            "Katiyadi 01713373484",
            "Bajittur 01713373485",
            "Kuliyarchar 01713373486",
            "Vairab 01713373487",
            "Itna 01713373488",
            "Mithamain 01713373489",
            "Nikli 01713373490",
            "Pakundiya 01713373491",
            "Astagram 01713373492",
            "Netrokona 01713373505",
            "Barahatta 01713373506",
            "Kalmakanda 01713373507",
            "Atpara 01713373508",
            "Durgapur 01713373509",
            "PurboDhala 01713373510",
            "Kenduya 01713373511",
            "Modon 01713373512",
            "Mohanganj 01713373513",
            "Khalijuri 01713373514",
            "Sherpur 01713373523",
            "Nokla 01713373524",
            "Nalitabari 01713373525",
            "Shribardi 01713373526",
            "Jhinaigati 01713373527",
            "Jamalpur 01713373538",
            "Melandah 01713373539",
            "Sharisabari 01713373540",
            "Deoyanganj 01713373541",
            "Islampur 01713373542",
            "Madarganj 01713373543",
            "Baksiganj 01713373544",
            "Bahadurabad 01713373545",
            "FaridpurKotwali 01713373556",
            "Madhukhali 01713373557",
            "Boyalmari 01713373558",
            "Alaphadanga 01713373559",
            "CharVodrosan 01713373560",
            "Nagarkanda 01713373561",
            "Sadarpur 01713373562",
            "Salta 01713373563",
            "Bhanga 01713373564",
            "Gopalganj 01713373572",
            "Makasudpur 01713373573",
            "Kasiyani 01713373574",
            "Kotalipara 01713373575",
            "Tungipara 01713373576",
            "Madaripur 01713373585",
            "Rajoir 01713373586",
            "Kalkini 01713373587",
            "Sibachar 01713373588",
            "Rajbari 01713373598",
            "Baliakandi 01713373599",
            "Pangsha 01713373600",
            "Goyalanda 01713373601",
            "Gosairha 01713373612",
            "Vedarganj 01713373613",
            "Damuddya 01713373614",
            "Jajira 01713373615",
            "Nariya 01713373616",
            "Palang 01713373617",
            "Shakhipur 01713373618"
    };

    String chittagongPoliceStationNumbers[] = {
            "Kotwali 01713373256",
            "Pahartali 01713373257",
            "Panchlaish 01713373258",
            "Chandgao 01713373259",
            "Khulasi 01713373260",
            "Bakoliya 01713373261",
            "BayezidBostami 01713373262",
            "Bandar 01713373267",
            "DoubleMuring 01713373268",
            "Halisahar 01713373269",
            "Patenga 01713373270",
            "Karnaphuli 01713373271",
            "Immigration(Bandar) 01713373272",
            "Pahartali(BandarZone) 01713373273",
            "Raujan 01713373639",
            "Hathajari 01713373640",
            "Fatikchari 01713373641",
            "Ranguniya 01713373642",
            "Potiya 01713373643",
            "Mireswarai 01713373644",
            "Sitakunda 01713373645",
            "Anowara 01713373646",
            "Boyalkhali 01713373647",
            "Bashkhali 01713373648",
            "Satkaniya 01713373649",
            "Lohagora 01713373650",
            "Candnais 01713373651",
            "Sandwip 01713373652",
            "Cox’sBazar 01713373663",
            "Ramu 01713373664",
            "Ukhiya 01713373665",
            "Tekanaf 01713373666",
            "Chakoriya 01713373667",
            "Kutubdiya 01713373668",
            "Maheskhali 01713373669",
            "Pekuya 01713373670",
            "ComillaKotwali 01713373685",
            "Chauddagram 01713373686",
            "Debirdwar 01713373687",
            "Homna 01713373688",
            "Laksam 01713373689",
            "Daudkandi 01713373690",
            "Buricang 01713373691",
            "Chandina 01713373692",
            "Barura 01713373693",
            "Langalakot 01713373694",
            "Muradnagar 01713373695",
            "Brammanpara 01713373696",
            "Meghna 01713373697",
            "Manoharganj 01713373698",
            "Titas 01713373699",
            "SouthComilla 01713373700",
            "Chandpur 01713373712",
            "Hajiganj 01713373713",
            "Motlab 01713373714",
            "MotlabUttar 01713373714",
            "MotlabSouth 01713373715",
            "Shaharasti 01713373716",
            "Kachuya 01713373717",
            "Faridaganj 01713373718",
            "Haimchar 01713373719",
            "BrammanBariya 01713373730",
            "Sarail 01713373731",
            "Ashuganj 01713373732",
            "Nasirnagar 01713373733",
            "Nabinagar 01713373734",
            "Bancharampur 01713373735",
            "Kosba 01713373736",
            "Akhaura 01713373737",
            "SudharamNoakhali -01713373748",
            "Begamganj 01713373749",
            "Senbag 01713373750",
            "Sonaimuri 01713373751",
            "Companyganj 01713373752",
            "Chatkhil 01713373753",
            "Hatiya 01713373754",
            "CharJabbar 01713373755",
            "Laxmipur 01713373765",
            "Raypur 01713373766",
            "Ramganj 01713373767",
            "Ramgati 01713373768",
            "Feni 01713373778",
            "Sonagaji 01713373779",
            "Fulgaji 01713373780",
            "Parshuram 01713373781",
            "Chagalnaiya 01713373782",
            "Dagonbhuiya 01713373783"
    };

    String rajshahiRangpurPoliceStationNumbers[] = {

            "Boyaliya 01713373309",
            "Rajpara 01713373310",
            "Matihar 01713373311",
            "Shah Makhdum 01713373312",
            "Paba 01713373800",
            "Gudagari 01713373801",
            "Tanor 01713373802",
            "Mohonpur 01713373803",
            "Puthiya 01713373804",
            "Bagmara 01713373805",
            "Durgapur 01713373806",
            "Carghata 01713373807",
            "Bagha 01713373808",
            "ChapaiNawbganj 01713373819",
            "Shibganj 01713373820",
            "Gomastapur 01713373821",
            "Nachol 01713373822",
            "Volahat 01713373823",
            "Naogoan 01713373836",
            "Raynagar 01713373837",
            "Atrai 01713373838",
            "Dhamrai 01713373839",
            "Badalgachi 01713373840",
            "Mahadebpur 01713373841",
            "Potnitola 01713373842",
            "Niyamatpur 01713373843",
            "Manda 01713373844",
            "Shapahar 01713373845",
            "Porsha 01713373846",
            "Nator 01713373857",
            "Shingra 01713373858",
            "Baghatipara 01713373859",
            "Gurudaspur 01713373860",
            "Lalapur 01713373861",
            "Baraigram 01713373862",
            "Noldanga 01713373863",
            "Rangpur 01713373874",
            "GongaChora 01713373875",
            "Bodarganj 01713373876",
            "Taraganj 01713373877",
            "Mithapukur 01713373878",
            "Pirgacha 01713373879",
            "Kauniya 01713373880",
            "Pirganj 01713373881",
            "Gaibandha 01713373892",
            "Sadullapur 01713373893",
            "Sundarganj 01713373894",
            "Palashbari 01713373895",
            "Gobindogonj 01713373896",
            "Saoghata 01713373897",
            "Fulachari 01713373898",
            "Nilfamari 01713373909",
            "Sayedpur 01713373910",
            "Joldhaka 01713373911",
            "KishorGonj(Nilphamari) 01713373912",
            "Domar 01713373913",
            "Dimla 01713373914",
            "Kurigram 01713373926",
            "Rajarhat 01713373927",
            "Fulabari 01713373928",
            "Nageswari 01713373929",
            "Burungamari 01713373930",
            "Ulipur 01713373931",
            "Chilmari 01713373932",
            "Roumari 01713373933",
            "Rajibpur 01713373934",
            "Dhusamara 01713373935",
            "CoChakata 01713373936",
            "Lalmanirhat 01713373946",
            "Aditmari 01713373947",
            "Kaliganj 01713373948",
            "Hatibandha 01713373949",
            "Patgram 01713373950",
            "DinajpurKotwali -01713373963",
            "ChirirBondor 01713373964",
            "Birol 01713373965",
            "Parbatipur 01713373966",
            "Birganj 01713373967",
            "Bochaganj 01713373968",
            "Kaharula 01713373969",
            "Khansama 01713373970",
            "Fulabari 01713373971",
            "Birampur 01713373972",
            "Nawabganj(Dinajpur) 01713373973",
            "Ghoraghat 01713373974",
            "Hakimpur 01713373975",
            "Thakurgaon 01713373985",
            "Baliadangi -01713373986",
            "Ranisankaoil 01713373987",
            "Pirganj 01713373988",
            "Haripur 01713373989",
            "Panchagar 01713373999",
            "Boda 01713374000",
            "Atoyari 01713374001",
            "Tetuliya 01713374002",
            "Debiganj 01713374003",
            "Pabna 01713374016",
            "Iswardi 01713374017",
            "Atghariya 01713374018",
            "Chatmohar 01713374019",
            "Bhangura 01713374020",
            "Faridpur(Pabna) 01713374021",
            "Surjonagar 01713374022",
            "Bera 01713374023",
            "Sathiya 01713374024",
            "Ataikula 01713374025",
            "Sirajganj 01713374038",
            "Sahajadpur 01713374039",
            "Ullapara 01713374040",
            "Cauhali 01713374041",
            "Tarash 01713374042",
            "Kajipur 01713374043",
            "Kamarkhanda 01713374044",
            "Rayganj 01713374045",
            "Belkuchi 01713374046",
            "JamunaBridgeWest 01713374047",
            "Salanga 01713374048",
            "Enayetpur 01713374049",
            "Bogra 01713374061",
            "Shibganj 01713374062",
            "Sonatola 01713374063",
            "Gabtoli 01713374064",
            "Sariakandi 01713374065",
            "Adamdighi 01713374066",
            "Dhupchachiya 01713374067",
            "Kahalu 01713374068",
            "Sherpur 01713374069",
            "Dhunat 01713374070",
            "Nandigram 01713374071",
            "Sahajahanpur 01713374072",
            "Jaypurahat 01713374082",
            "Kalai 01713374083",
            "Khetlal 01713374084",
            "Akkelpur 01713374085",
            "Pachbibi 01713374086"
    };
    String khulnaPoliceStationNumbers[] = {
            "Khulna 01713373285",
            "Sonadanga 01713373286",
            "Khalisapur 01713373287",
            "Daulatapur 01713373288",
            "Khan Jahan Ali 01713373289",
            "Fultola 01713374103",
            "Digholiya 01713374104",
            "Paikgacha 01713374105",
            "Batiyaghata 01713374106",
            "Dumuriya 01713374107",
            "Terokhada 01713374108",
            "Rupsa 01713374109",
            "Dakopa 01713374110",
            "Koyara 01713374111",
            "Bagerhat 01713374122",
            "Fakirhat 01713374123",
            "Mollarhat 01713374124",
            "Chitalmari 01713374125",
            "Kachuya 01713374126",
            "Moralganj 01713374127",
            "Sharankhola 01713374128",
            "Mongla 01713374129",
            "Rampal 01713374130",
            "Satkhira 01713374141",
            "Kolaroya 01713374142",
            "Tala 01713374143",
            "Kaliganj 01713374144",
            "Shyamnagar 01713374145",
            "Debhata 01713374146",
            "Ashashuni 01713374147",
            "Patkelghata 01713374148",
            "JessoreKotwali 01713374161",
            "Jhikargacha 01713374162",
            "Sharsha 01713374163",
            "Caugacha 01713374164",
            "Manirampur 01713374165",
            "Kesobpur 01713374166",
            "Ovoynagar 01713374167",
            "Bagharpara 01713374168",
            "Benapole 01713374169",
            "BenapolCheckPost 01713374170",
            "Magura 01713374179",
            "Salikha 01713374180",
            "Shripur 01713374181",
            "Mohammadapur 01713374182",
            "Jhinaidah 01713374192",
            "Kaliganj 01713374193",
            "Shailkupa 01713374194",
            "Horinakundu 01713374195",
            "Kotchadpur 01713374196",
            "Maheshpur 01713374197",
            "Norail 01713374206",
            "Kaliya 01713374207",
            "Lohagora 01713374208",
            "Naragati 01713374209",
            "Kushtiya 01713374220",
            "Khoksa 01713374221",
            "Kumarkhali 01713374222",
            "Veramara 01713374223",
            "Daulatpur 01713374224",
            "Mirpur 01713374225",
            "IslamicUniversity 01713374226",
            "Chuyadanga 01713374236",
            "Alamdanga 01713374237",
            "Jibannagar 01713374238",
            "Damurhuda 01713374239",
            "Meherpur 0171337424",
            "Gangni 01713374250",
            "Mujibnagar 01713374251"
    };

    String barishalPoliceStationNumbers[] = {
            "BarisalKotwali 01713374267",
            "Hijla 01713374268",
            "Mehediganj 01713374269",
            "Muladi 01713374270",
            "Babuganj 01713374271",
            "Bakerganj 01713374272",
            "Banaripara 01713374273",
            "Agauljhara 01713374274",
            "Gauronodi 01713374275",
            "Ujirpur 01713374276",
            "Jhalkathi 01713374286",
            "Nolchithi 01713374287",
            "Rajapur 01713374288",
            "Kathaliya 01713374289",
            "Vola 01713374300",
            "Daulatkhan 01713374301",
            "Tajumuddin 01713374302",
            "Borahanuddin 01713374303",
            "Lalmohan 01713374304",
            "Charfasion 01713374305",
            "Monpura 01713374306",
            "Potuyakhali 01713374318",
            "Baufal 01713374319",
            "Golachipa 01713374320",
            "Doshmina 01713374321",
            "Dumki 01713374322",
            "Kolapara 01713374323",
            "Mirjaganj 01713374324",
            "Rangabali 01713374325",
            "Pirojpur 01713374336",
            "Vavdariya 01713374337",
            "Nesarabad 01713374338",
            "Kaukhali 01713374339",
            "Najirapur 01713374340",
            "Zianagar 01713374341",
            "Mothbariya 01713374342",
            "Borguna 01713374353",
            "Amtali 01713374354",
            "Pathorghata 01713374355",
            "Betagi 01713374356",
            "Bamona 01713374357",
            "Taltoli 01713374358"
    };

    String sylhetPoliceStationNumbers[] = {
            "KotowaliSylhet 01713374375",
            "Balaganj 01713374376",
            "Jaintapur 01713374377",
            "Goyainghat 01713374378",
            "Kanaighat 01713374379",
            "Companyganja 01713374380",
            "Jokiganj 01713374381",
            "Biyanibazar 01713374382",
            "Golapganj 01713374383",
            "Biswonath 01713374384",
            "Fenchuganj 01713374385",
            "South Surma 01713374386",
            "OsmaniNagar 01713374387",
            "Habiganj 01713374398",
            "Madhabpur 01713374399",
            "Chunarughat 01713374400",
            "Bahubol 01713374401",
            "Lakhai 01713374402",
            "Nabiganj 01713374403",
            "Baniyachang 01713374404",
            "Ajmiriganj 01713374405",
            "Shayestaganj 01713374406",
            "Sunamganj 01713374418",
            "Chatak 01713374419",
            "Jagannathpur 01713374420",
            "Tahirpur 01713374421",
            "Biswambarpur 01713374422",
            "Doyarabazar 01713374423",
            "Dirai 01713374424",
            "Shalna 01713374425",
            "Jamalaganj 01713374426",
            "Dharmapasa 01713374427",
            "Madhyanagar 01713374428",
            "Maulvibazar -01713374439",
            "Shrimangol 01713374440",
            "Komolaganj 01713374441",
            "Rajanagar 01713374442",
            "Kulaura 01713374443",
            "Borolekha 01713374444",
            "Juri 01713374445"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_contact_number);

        listView=(ListView)findViewById(R.id.listview);
        mySearchView = (SearchView) findViewById(R.id.searchView);
        textView = findViewById(R.id.locText);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        Bundle bundle = getIntent().getExtras();
        String divisionName = bundle.getString("name");
        //Toast.makeText(PoliceContactNumber.this,"Clicked item: "+divisionName,Toast.LENGTH_SHORT).show();

        //create ArrayList of String
        final ArrayList<String> arrayList=new ArrayList<>();

        switch(divisionName) {
            case "Dhaka":
                for(int i=0; i<dhakaPoliceStationNumbers.length;i++){
                    arrayList.add(dhakaPoliceStationNumbers[i]);
                }
                break;

            case "Chittagong":
                for(int i=0; i<chittagongPoliceStationNumbers.length;i++){
                    arrayList.add(chittagongPoliceStationNumbers[i]);
                }
                break;

            case "Rajshahi":
                for(int i=0; i<rajshahiRangpurPoliceStationNumbers.length;i++){
                    arrayList.add(rajshahiRangpurPoliceStationNumbers[i]);
                }
                break;

            case "Khulna":
                for(int i=0; i<khulnaPoliceStationNumbers.length;i++){
                    arrayList.add(khulnaPoliceStationNumbers[i]);
                }
                break;

            case "Barishal":
                for(int i=0; i<barishalPoliceStationNumbers.length;i++){
                    arrayList.add(barishalPoliceStationNumbers[i]);
                }
                break;

            case "Sylhet":
                for(int i=0; i<sylhetPoliceStationNumbers.length;i++){
                    arrayList.add(sylhetPoliceStationNumbers[i]);
                }
                break;

            case "Rangpur":
                for(int i=0; i<rajshahiRangpurPoliceStationNumbers.length;i++){
                    arrayList.add(rajshahiRangpurPoliceStationNumbers[i]);
                }
                break;

            case "Call to Nearest Police Station":
                makeNearestCall();
                break;

            case "Add Trusted Contact":

                Intent send = new Intent(PoliceContactNumber.this, AddTrustedContact.class);
                startActivity(send);
                break;

            case "Send SMS to Trusted Contact":
                if(ContextCompat.checkSelfPermission(PoliceContactNumber.this,Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                    sendMessage2();
                }
                else{
                    ActivityCompat.requestPermissions(PoliceContactNumber.this,new String[]{Manifest.permission.SEND_SMS},100);
                }

                break;
            default:
                Toast.makeText(com.example.otplogin.PoliceContactNumber.this,"This division is not available",Toast.LENGTH_SHORT).show();
                break;
        }



//Add elements to arraylist

        //Create Adapter
        ArrayAdapter arrayAdapter=new ArrayAdapter(this, R.layout.list_view_text_color,R.id.colorListView,arrayList);

//assign adapter to listview
        listView.setAdapter(arrayAdapter);

//add listener to listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(Dhaka.this,"clicked item:"+i+" "+arrayList.get(i).toString(),Toast.LENGTH_SHORT).show();

                String details = arrayList.get(i);
                String[] phone = details.split(" ");
                String parsedNumber = phone[1];
                parsedNumber = "tel:" + parsedNumber;

                Intent call = new Intent(Intent.ACTION_DIAL);
                //call.setData(Uri.parse("tel:01521415061"));
                call.setData(Uri.parse(parsedNumber));
                startActivity(call);

            }
        });




        mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });




    }

    String s;

    public void makeNearestCall(){

        readFile();
        getCurrentLocation();

    }


    private void sendMessage2(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(PoliceContactNumber.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if (isGPSEnabled()) {

                    LocationServices.getFusedLocationProviderClient(PoliceContactNumber.this)
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);

                                    LocationServices.getFusedLocationProviderClient(PoliceContactNumber.this)
                                            .removeLocationUpdates(this);

                                    if (locationResult != null && locationResult.getLocations().size() >0){

                                        int index = locationResult.getLocations().size() - 1;
                                        double latitude = locationResult.getLocations().get(index).getLatitude();
                                        double longitude = locationResult.getLocations().get(index).getLongitude();


                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                        String uid = user.getUid();



                                        reference = FirebaseDatabase.getInstance().getReference().child("Trusted Contact");

                                        reference.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                String [] values2;
                                                String m = null;
                                                for(DataSnapshot dataSnapShot : snapshot.getChildren() ){
                                                    //C value = dataSnapShot.getValue();
                                                    //String value = String.valueOf(dataSnapShot);
                                                    //count = dataSnapShot.getChildrenCount();


                                                    String key = dataSnapShot.getKey();
                                                    textView.setText("Key :" + key);
                                                    if(key.equals(uid)){

                                                        String number = "+88" + dataSnapShot.getValue().toString();
                                                        String message = "User's location : \nLatitude : " + latitude + " Longitude : " +longitude;

                                                        SmsManager smsManager = SmsManager.getDefault();
                                                        smsManager.sendTextMessage(number,null,message,null,null);
                                                    }


                                                }


                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });




                                        //String number = task.getResult().toString();


                                        //databaseReference.child(uid).setValue(contact);

                                        //AddressText.setText("Latitude: "+ latitude + "\n" + "Longitude: "+ longitude);



                                    }
                                }
                            }, Looper.getMainLooper());


                } else {
                    turnOnGPS();
                }

            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }

        }

    }






    public void readFile(){

        BufferedReader reader;
        String [] values;
        int count = 0;
        try{
            final InputStream file = getAssets().open("policeLocations.txt");
            //final InputStream file = getAssets().open("RandomLatLong.txt");
            reader = new BufferedReader(new InputStreamReader(file));
            String line = reader.readLine();
            while(line != null){
                values = line.split(" ");
                //Toast.makeText(this, line, Toast.LENGTH_LONG).show();
                //LatLng test = new LatLng(Double.parseDouble(values[0]), Double.parseDouble(values[1]));

                //locations.add(new Coordinate(Double.parseDouble(values[0]), Double.parseDouble(values[1])));
                NearestPolice temp = new NearestPolice(values[0],Double.parseDouble(values[1]), Double.parseDouble(values[2]),values[3]);
                policeStationLatLng.add(temp);
                count ++;
                line = reader.readLine();
            }
        } catch(IOException ioe){
            ioe.printStackTrace();
        }
        //Toast.makeText(this, "Data "+count, Toast.LENGTH_LONG).show();

    }

    public double calculateDistance (LatLng c1, LatLng c2)
    {
        return Math.sqrt(
                Math.pow(c1.latitude - c2.latitude,2)+
                        Math.pow(c1.longitude - c2.longitude,2));
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){

                if (isGPSEnabled()) {

                    getCurrentLocation();

                }else {

                    turnOnGPS();
                }
            }
        }

//        if(requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//            sendMessage2();
//        }
//        else {
//            Toast.makeText(getApplicationContext(),"Permission Denied",Toast.LENGTH_SHORT).show();
//        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {

                getCurrentLocation();
            }
        }
    }

    String t = new String();

    public void getCurrentLocation() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(PoliceContactNumber.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if (isGPSEnabled()) {

                    LocationServices.getFusedLocationProviderClient(PoliceContactNumber.this)
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);

                                    LocationServices.getFusedLocationProviderClient(PoliceContactNumber.this)
                                            .removeLocationUpdates(this);

                                    if (locationResult != null && locationResult.getLocations().size() >0){

                                        int index = locationResult.getLocations().size() - 1;
                                        double latitude = locationResult.getLocations().get(index).getLatitude();
                                        double longitude = locationResult.getLocations().get(index).getLongitude();

                                        //AddressText.setText("Latitude: "+ latitude + "\n" + "Longitude: "+ longitude);
                                        String print = latitude + " Lat Lng"+ longitude;
                                        t = print;
                                        nearestP = print;
                                        LatLng test = new LatLng(latitude, longitude);
                                        textView.setText(print);
                                        LatLng locationT = new LatLng(latitude, longitude);

                                        LatLng iit = new LatLng(23.728916, 90.398404);

                                        double smallest = 1000.00;
                                        String nearP = null;
                                        String phoneN = null;


                                        for (NearestPolice np : policeStationLatLng){
                                            LatLng pl = new LatLng(np.getLatitude(),np.getLongitude());

                                            double distance = calculateDistance(locationT,pl);

                                            if(distance < smallest){
                                                smallest = distance;
                                                nearP = np.getName();
                                                phoneN = np.getPhoneNumber();
                                            }
                                        }

                                        textView.setText(nearP + " Police Station");

                                        phoneN = "tel:" + phoneN;

                                        Intent call = new Intent(Intent.ACTION_DIAL);
                                        call.setData(Uri.parse(phoneN));
                                        startActivity(call);

                                    }
                                }
                            }, Looper.getMainLooper());


                } else {
                    turnOnGPS();
                }

            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }

        }
        //Toast.makeText(this, "Nearest Police Station : " +t, Toast.LENGTH_LONG).show();


    }

    private void turnOnGPS() {



        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(PoliceContactNumber.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(PoliceContactNumber.this, 2);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            //Device does not have location
                            break;
                    }
                }
            }
        });

    }

    private boolean isGPSEnabled() {
        LocationManager locationManager = null;
        boolean isEnabled = false;

        if (locationManager == null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }

        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;

    }
}