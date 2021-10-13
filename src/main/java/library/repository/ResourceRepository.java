package library.repository;

import library.model.Resource;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ResourceRepository extends MongoRepository<Resource, ObjectId> {
}
