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
    protected $mock;

    public function setUp() : void{
        $this->mock = $this->createMock('database\QueryBuilder');
        $this->mock->expects($this->any())->method('getSql')->will($this->returnValue("SELECT * FROM Religion WHERE name = :name AND id > :id"));
        $this->religion = new database\Religion($this->mock);
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
        $this->assertEquals(
            '22001',
            $this->religion->insert(new \model\Religion('THHHHHHHHHHHHHHIIIIIIIIIIIIIIIIIIIIISSSSSSSSSSSSSSSSSSSSSSSSSSIIIIIIIIIIIIIIISSSSSSSSSSSSSSSSLLLLLLONNNNNNG'))
        );
    }

    public function testSelect(): void
    {
        $this->assertEquals(
            '00000',
            $this->religion->select(new \model\Religion('religieTest', 0))
        );
    }
}
