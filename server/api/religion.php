<?php
namespace api;
require_once(dirname(__FILE__,2) . '/model/Religion.php');
require_once(dirname(__FILE__,2) . '/database/Religion.php');
require_once(dirname(__FILE__,2) . '/exception/NullPointerException.php');
require_once(dirname(__FILE__,1) . '/Api.php');

class Religion extends Api{

    private $model;

    function __construct(){
        parent::__construct();
        set_error_handler(array($this, 'error_handler'));
    }

    function insert() : void{
        try{
            $this->model = new \model\Religion($_GET['name']);

            $religieStatement = new \database\Religion();
            $code = $religieStatement->insert($this->model);

            $code = substr($code, 0, 2);

            parent::setHttpCode($code);

        }catch(\PDOException $e){
            parent::setHttpCode($e->getCode());
        }catch(\exception\NullPointerException $e){
            header('HTTP/1.0 400 Bad Request');
            restore_error_handler();
        }
    }

    function error_handler(){
        if($errstr == 'Undefined index: name'){
            throw new \exception\NullPointerException("Get value isn't passed");
        }else{
            restore_error_handler();
        }
    }
}

$religion = new Religion();
$religion->insert();
?>
