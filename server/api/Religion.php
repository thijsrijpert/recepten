<?php
namespace api;
<<<<<<< Updated upstream
require_once('../model/Religion.php');
require_once('../database/Religion.php');
require_once('../exception/NullPointerException.php');
=======
echo 'test';
require_once('../model/Religion.php');
echo 'test';
require_once('../database/Religion.php');
echo 'test3';
require_once('../exception/NullPointerException.php');
echo 'test4';
>>>>>>> Stashed changes
require_once('Api.php');

class Religion extends Api{

    private $model;

    function __construct(){
        parent::__construct();
<<<<<<< Updated upstream
        //set_error_handler('error_handler');
=======
        set_error_handler(array($this, 'error_handler'));
>>>>>>> Stashed changes
    }

    function insert() : void{
        try{
<<<<<<< Updated upstream
            $this->model = new model\Religion($_GET['name']);

            $religieStatement = new database\Religion();
=======
            $this->model = new \model\Religion($_GET['name']);

            $religieStatement = new \database\Religion();
>>>>>>> Stashed changes
            $code = $religieStatement->insert($this->model);

            $code = substr($code, 0, 2);

            parent::setHttpCode($code);

        }catch(\PDOException $e){
            parent::setHttpCode($e->getCode());
<<<<<<< Updated upstream
        }catch(exception\NullPointerException $e){
            header('HTTP/1.0 400 Bad Request');
=======
        }catch(\exception\NullPointerException $e){
            header('HTTP/1.0 400 Bad Request');
            restore_error_handler();
>>>>>>> Stashed changes
        }
    }

    function error_handler(){
<<<<<<< Updated upstream
        throw new exception\NullPointerException("Get value isn't passed");
    }
}

$religie = new Religie();
$religie->insert();
=======
        if($errstr == 'Undefined index: name'){
            throw new \exception\NullPointerException("Get value isn't passed");
        }else{
            restore_error_handler();
        }
    }
}

$religion = new Religion();
$religion->insert();
>>>>>>> Stashed changes
?>
