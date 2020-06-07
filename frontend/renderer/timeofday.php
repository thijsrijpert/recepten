<?php
namespace frontend\renderer;
ini_set('display_startup_errors', 1);
ini_set('display_errors', 1);
error_reporting(E_ALL | E_STRICT);
require_once(dirname(__FILE__, 2) . '/connector/TimeOfDay.php');
require_once(dirname(__FILE__,2) . '/exception/UserException.php');
require_once(dirname(__FILE__,1) . '/Renderer.php');
class TimeOfDay extends Renderer{

    public function __construct(){
        $this->connector = new \frontend\connector\TimeOfDay();
    }

    public function loadOverview(){
        try{
            $response = json_decode($this->connector->select(), true);

            foreach($response as $value){
                include(dirname(__FILE__,2) . '/html/overview-row.php');
            }
        }catch(\frontend\exception\UserException $e){
            parent::loadErrorMessage($e->getMessage());
        }
    }

    public function loadInsert(){
        include(dirname(__FILE__,2) . '/html/insert-form.php');
    }
}
$renderer = new TimeOfDay();
if(isset($_GET['action'])){
    if($_GET['action'] == 'overview'){
        $renderer->loadOverview();
    }else if($_GET['action'] == 'insert'){
        $renderer->loadInsert();
    }
}

?>
