<?php
declare(strict_types=1);
use PHPUnit\Framework\TestCase;
require_once(dirname(__FILE__, 3) .'\server\model\Wordfilter.php');
require_once(dirname(__FILE__, 3) . '\server\database\Wordfilter.php');
require_once(dirname(__FILE__, 3) . '\server\exception\NullPointerException.php');




final class WordfilterDatabaseTest extends TestCase
{

    protected $wordfilter;
    protected $mock;

    public function setUp() : void{
        $this->mock = $this->createMock('database\QueryBuilder');
        $this->mock->expects($this->any())->method('getSql')->will($this->returnValue("SELECT * FROM Wordfilter"));
        $this->wordfilter = new database\Wordfilter($this->mock);
    }

    public function testInsert(): void{
        $this->assertEquals(
            '00000',
            $this->wordfilter->insert(new \model\Wordfilter('lul'))
        );
        $this->assertEquals(
            '23000',
            $this->wordfilter->insert(new \model\Wordfilter('lul',0,0))
        );
        $this->assertEquals(
            '22001',
            $this->wordfilter->insert(new \model\Wordfilter('lulloooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo'))
        );
    }
}
