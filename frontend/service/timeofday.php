<?php
namespace frontend\service;
ini_set('display_startup_errors', 1);
ini_set('display_errors', 1);
error_reporting(E_ALL | E_STRICT);
require_once(dirname(__FILE__, 2) . '/connector/TimeOfDay.php');
require_once(dirname(__FILE__, 2) . '/model/TimeOfDay.php');
require_once(dirname(__FILE__, 2) . '/renderer/timeofday.php');
require_once(dirname(__FILE__,2) . '/exception/UserException.php');

class TimeOfDay {

    private $connector;
    private $renderer;
    public function __construct(){
        $this->connector = new \frontend\connector\TimeOfDay();
        $this->renderer = new \frontend\renderer\TimeOfDay();
    }

    public function insert(\model\TimeOfDay $model){
        try{
            $this->connector->insert($model);
            $this->renderer->loadOverview();
        }catch(\frontend\exception\UserException $e){
            $this->renderer->loadInsert();
            $this->renderer->loadErrorMessage($e->getMessage());
        }
    }
}
$service = new TimeOfDay();
if(isset($_POST['action'])){
    if($_POST['action'] == 'insert'){
        $service->insert(new \model\TimeOfDay($_POST['object']['name']));
    }
}

?>
