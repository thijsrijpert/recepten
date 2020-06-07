<?php
namespace frontend\connector;
require_once(dirname(__FILE__,2) . '/exception/UserException.php');
class Connector {

    public function __construct(){

    }

    public function get(String $url) : String{
        $connection = curl_init();

        curl_setopt($connection, CURLOPT_URL, $url);
        curl_setopt($connection, CURLOPT_RETURNTRANSFER, true);

        $output = curl_exec($connection);

        if(curl_getinfo($connection, CURLINFO_RESPONSE_CODE) == 200){
            curl_close($connection);
            return $output;
        }else if(curl_getinfo($connection, CURLINFO_RESPONSE_CODE) == 400){
            curl_close($connection);
            throw new \frontend\exception\UserException("De ingevulde gegevens zijn incorrect, probeer eens wat anders");
        }else if(curl_getinfo($connection, CURLINFO_RESPONSE_CODE) == 503){
            curl_close($connection);
            throw new \frontend\exception\UserException("Het lijkt erop dat de servers op dit moment niet bereikbaar zijn, probeer het later nog eens");
        }else if(curl_getinfo($connection, CURLINFO_RESPONSE_CODE) == 500){
            curl_close($connection);
            throw new \frontend\exception\UserException("Er is een onbekend probleem opgetreden, neem contact op met een support medewerker");
        }
    }
}

?>
