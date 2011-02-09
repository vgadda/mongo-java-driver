// MapReduceOutput.java

package com.mongodb;

/**
 * Represents the result of a map/reduce operation
 * @author antoine
 */
public class MapReduceOutput {

    MapReduceOutput( DBCollection from , BasicDBObject raw ){
        _raw = raw;

        if ( raw.containsKey( "results" ) ) {
            _coll = null;
            _collname = "<<INLINE>>";
            _resultSet = (Iterable<DBObject>) raw.get( "results" );
        } else {
            _collname = raw.getString( "result" );
            _coll = from._db.getCollection( _collname );
            _resultSet = _coll.find();
        }
        _counts = (BasicDBObject)raw.get( "counts" );
    }

    /**
     * returns a cursor to the results of the operation
     * @return
     */
    public Iterable<DBObject> results(){
        return _resultSet;
    }

    /**
     * drops the collection that holds the results
     */
    public void drop(){
        if ( _coll != null)
            _coll.drop();
    }
    
    /**
     * gets the collection that holds the results
     * (Will return null if results are Inline)
     * @return
     */
    public DBCollection getOutputCollection(){
        return _coll;
    }

    public BasicDBObject getRaw(){
        return _raw;
    }

    public String toString(){
        return _raw.toString();
    }
    
    final BasicDBObject _raw;

    final String _collname;
    final Iterable<DBObject> _resultSet;
    final DBCollection _coll;
    final BasicDBObject _counts;
}
