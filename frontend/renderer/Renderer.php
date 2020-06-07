<?php
namespace frontend\renderer;
ini_set('display_startup_errors', 1);
ini_set('display_errors', 1);
error_reporting(E_ALL | E_STRICT);
require_once(dirname(__FILE__, 2) . '/connector/TimeOfDay.php');
class Renderer{

    protected $connector;
    public function __construct(){

    }

    public function loadErrorMessage(String $error){
        include(dirname(__FILE__,2) . '/html/error-message.php');
    }
}

?>
