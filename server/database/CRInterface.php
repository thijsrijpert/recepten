<?php
namespace database;
interface CRInterface {
    abstract function select(\model\Model $model) : array;

    abstract function insert(\model\Model $model) : String;

    abstract function error_handler($errno, $errstr, $errfile, $errline);
}
