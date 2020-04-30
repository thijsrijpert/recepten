<?php
namespace exception;
class  InvalidRequestException extends \Exception{
    public function __construct(String $message = 'The url contains data that is not allowed', int $code = 0, Exception $previous = null){
        parent::__construct($message, $code, $previous);
    }
}
?>
