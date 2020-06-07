<?php
namespace frontend\exception;
class UserException extends \Exception{
    public function __construct(String $message = "", int $code = 0, Exception $previous = null){
        parent::__construct($message, $code, $previous);
    }
}

 ?>
