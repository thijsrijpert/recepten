<?php
declare(strict_types=1);
use PHPUnit\Framework\TestCase;
require_once(dirname(__FILE__, 3) . '\server\exception\NullPointerException.php');
require_once(dirname(__FILE__, 3) . '\server\database\QueryBuilderUpdate.php');
require_once(dirname(__FILE__, 3) . '\server\database\QueryUpdate.php');
require_once(dirname(__FILE__, 3) . '\server\model\Review.php');


final class QueryBuilderUpdateTest extends TestCase
{
    private $queryBuilder;
    private $mock;

    public function setUp() : void{

        $this->mock = $this->createMock('database\QueryUpdate');
        $this->queryBuilder = new \database\QueryBuilderUpdate($this->mock);
    }

    public function testGenerateSql(): void
    {
        $output = "UPDATE Review SET title = :titleUpdate WHERE id = :id";

        $this->mock->expects($this->any())->method('getSetArguments')->will($this->returnValue([['title']]));
        $this->mock->expects($this->any())->method('getWhereArguments')->will($this->returnValue([['id', '=']]));
        $this->mock->expects($this->any())->method('getEntity')->will($this->returnValue(new \model\Review()));

        $this->queryBuilder->setQuery($this->mock);

        $this->assertEquals(
            true,
            $this->queryBuilder->generateSql()
        );
        $this->assertEquals(
            $output,
            $this->queryBuilder->getSql()
        );
    }

    public function testGenerateMultipleSet(): void
    {
        $output = "UPDATE Review SET title = :titleUpdate, rating = :ratingUpdate WHERE id = :id";

        $this->mock->expects($this->any())->method('getSetArguments')->will($this->returnValue([['title'], ['rating']]));
        $this->mock->expects($this->any())->method('getWhereArguments')->will($this->returnValue([['id', '=']]));
        $this->mock->expects($this->any())->method('getEntity')->will($this->returnValue(new \model\Review()));

        $this->queryBuilder->setQuery($this->mock);

        $this->assertEquals(
            true,
            $this->queryBuilder->generateSql()
        );
        $this->assertEquals(
            $output,
            $this->queryBuilder->getSql()
        );
    }

    public function testGenerateSqlNoWhere(): void
    {
        $output = "";

        $this->mock->expects($this->any())->method('getSetArguments')->will($this->returnValue([['title']]));
        $this->mock->expects($this->any())->method('getWhereArguments')->will($this->returnValue([]));
        $this->mock->expects($this->any())->method('getEntity')->will($this->returnValue(new \model\Review()));

        $this->queryBuilder->setQuery($this->mock);

        $this->assertEquals(
            false,
            $this->queryBuilder->generateSql()
        );
        $this->assertEquals(
            $output,
            $this->queryBuilder->getSql()
        );
    }

    public function testGenerateSqlNoSet(): void
    {
        $output = "";
        $this->mock->expects($this->any())->method('getSetArguments')->will($this->returnValue([]));
        $this->mock->expects($this->any())->method('getWhereArguments')->will($this->returnValue([['id', '=']]));
        $this->mock->expects($this->any())->method('getEntity')->will($this->returnValue( new \model\Review()));

        $this->queryBuilder->setQuery($this->mock);

        $this->assertEquals(
            false,
            $this->queryBuilder->generateSql()
        );
        $this->assertEquals(
            $output,
            $this->queryBuilder->getSql()
        );
    }
}
