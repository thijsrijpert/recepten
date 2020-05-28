<?php
declare(strict_types=1);
use PHPUnit\Framework\TestCase;
require_once(dirname(__FILE__, 3) .'\server\model\Review.php');
require_once(dirname(__FILE__, 3) .'\server\model\User.php');
require_once(dirname(__FILE__, 3) . '\server\database\Review.php');
require_once(dirname(__FILE__, 3) . '\server\exception\NullPointerException.php');


final class ReligionDatabaseTest extends TestCase
{

    protected $religion;
    protected $mockUpdate;
    protected $mock;

    public function setUp() : void{
        $this->mock = $this->createMock('database\QueryBuilder');
        $this->mockUpdate = $this->createMock('database\QueryBuilderUpdate');
        $this->religion = new database\Religion();
    }

    public function testSelect(): void
    {
      $this->mock->expects($this->any())->method('getSql')->will($this->returnValue("SELECT * FROM Religion WHERE name = :name AND id > :id"));
      $this->religion = new database\Religion($this->mock);
        $this->assertEquals(
            array('00000', array(array(new \model\Religion('Moslim', 20193)))),
            $this->religion->select(new \model\Religion('Moslim', 0))
        );
    }

    public function testSelectOneWhere(): void
    {
      $this->mock->expects($this->any())->method('getSql')->will($this->returnValue("SELECT * FROM Religion WHERE name = :name"));
      $this->religion = new database\Religion($this->mock);
        $this->assertEquals(
            array('00000', array(array(new \model\Religion('Moslim', 20193)))),
            $this->religion->select(new \model\Religion('Moslim'))
        );
    }

    public function testSelectNoWhere(): void
    {
      $this->mock->expects($this->any())->method('getSql')->will($this->returnValue("SELECT * FROM Religion"));
      $this->religion = new database\Religion($this->mock);
        $this->assertEquals(
            array('00000', array(array(new model\Religion('Geloof 2', 20195), new model\Religion('Geloof 3', 20197), new model\Religion('Geloof 4', 20199), new model\Religion('Moslim', 20193)))),
            $this->religion->select(new \model\Religion())
        );
    }

    public function testUpdate(){
      $this->mockUpdate->expects($this->any())->method('getSql')->will($this->returnValue("UPDATE Religion SET name = :nameUpdate WHERE id = :id"));
      $this->religion = new database\Religion($this->mockUpdate);
        $this->assertEquals(
            '00000',
            $this->religion->update(new \model\Religion('updateTest'), new \model\Religion(null,20199))
        );
    }

    public function testInsert(): void
    {
        $this->assertEquals(
            '00000',
            $this->religion->insert(new \model\Religion('religieTest'))
        );
        $this->assertEquals(
            '23000',
            $this->religion->insert(new \model\Religion('religieTest'))
        );
        // $this->assertEquals(
        //     '22001',
        //     $this->religion->insert(new \model\Religion('THHHHHHHHHHHHHHIIIIIIIIIIIIIIIIIIIIISSSSSSSSSSSSSSSSSSSSSSSSSSIIIIIIIIIIIIIIISSSSSSSSSSSSSSSSLLLLLLONNNNNNG'))
        // );
    }
}
