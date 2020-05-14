<?php
namespace database;
interface CRInterface {
    function select(\model\Model $model) : array;

    function insert(\model\Model $model) : String;

    function error_handler($errno, $errstr, $errfile, $errline);
}
