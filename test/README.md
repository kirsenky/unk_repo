# unk_repo                                                                                                                                
                                                                                                                                          
# REST/gRPC performance test project                                                                                                      
                                                                                                                                          
# Test objectives                                                                                                                         
1. Create PostgreSQL DB                                                                                                                   
                                                                                                                                          
--- CREATE DATABASE collector_db;                                                                                                         
--- enable uuid-ossp CREATE EXTENSION IF NOT EXISTS "uuid-ossp";                                                                          
                                                                                                                                          
--- DB item ---                                                                                                                           
CREATE TABLE item (                                                                                                                       
id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,                                                                                           
path VARCHAR(256) NOT NULL,                                                                                                               
dsc VARCHAR(256) DEFAULT NULL                                                                                                             
);                                                                                                                                        
                                                                                                                                          
2. Create a REST Spring microservice to ADD / UPD / DEL items                                                                             
3. Create a gRPC Spring microservice to ADD / UPD / DEL                                                                                   
4. Benchmark the two above services by adding / updating / deleting 1 million items                                                       
5. Provide the code in a git repository with documentation in README.md (install, deploy, run) + benchmark results                        
                                                                                                                                          
NB:                                                                                                                                       
                                                                                                                                          
- licence can be GPL                                                                                                                      
- for accessing the database, use JOOQL for both subprojects                                                                              
==============================================================================
                                                                                                                                          
# Installation                                                                                                                            
1. Install Postgress (if not installed)                                                                                                   
   - run as postgress user commands:                                                                                                      
     ${deployment_folder}/test/db/scripts/create.bat  
     !!! if you installing on unix you need to create simiar bash script
     Then you will be asked to input password for users: 
            postgres - superuser under which account we can create test user for our purposes and add UUID extension.
            test - regular user created by create.bat with password which you can see in create.bat. 
                   This user is used to build and run tests.
2. Deploy from GIT https://github.com/kirsenky/unk_repo.git                                                                               
3. Run in main project folder (${deployment_folder}/test) command: 
   mvn -install                                                           
4. To start performance test:
   - run sever gRPC: 
     java -jar ${path_to_grpc_server_target_folder}/test.grpc.server-1.0-SNAPSHOT.jar   
   - run client gRPC: 
     java -jar ${path_to_grpc_client_target_folder}/test.grpc.client-1.0-SNAPSHOT.jar   
Similar commands are used for REST testing. All required to do so is to replace 'grpc' in above examples with 'rest'.

# Test evidence                                                                                                                           
# REST test run listing                                                                                                                   
2020-06-04 16:17:23.872 ...  : === Starting performance test for REST client/server ==                                                    
2020-06-04 16:18:16.938 ...  : Time spent for iteration 0 (100 records inserted/updated/deleted) is 53 Seconds                            
2020-06-04 16:19:04.259 ...  : Time spent for iteration 1 (100 records inserted/updated/deleted) is 47 Seconds                            
2020-06-04 16:20:11.552 ...  : Time spent for iteration 2 (100 records inserted/updated/deleted) is 67 Seconds                            
2020-06-04 16:20:57.738 ...  : Time spent for iteration 3 (100 records inserted/updated/deleted) is 46 Seconds                            
2020-06-04 16:21:44.936 ...  : Time spent for iteration 4 (100 records inserted/updated/deleted) is 47 Seconds                            
2020-06-04 16:22:31.418 ...  : Time spent for iteration 5 (100 records inserted/updated/deleted) is 46 Seconds                            
2020-06-04 16:23:17.384 ...  : Time spent for iteration 6 (100 records inserted/updated/deleted) is 45 Seconds                            
2020-06-04 16:24:02.293 ...  : Time spent for iteration 7 (100 records inserted/updated/deleted) is 44 Seconds                            
2020-06-04 16:24:48.873 ...  : Time spent for iteration 8 (100 records inserted/updated/deleted) is 46 Seconds                            
2020-06-04 16:25:34.895 ...  : Time spent for iteration 9 (100 records inserted/updated/deleted) is 46 Seconds                            
2020-06-04 16:25:34.895 ...  : === Performance test for REST client/server finished ==                                                    
                                                                                                                                          
# gRPC test run listing                                                                                                                   
23:11:50.942 [main] INFO ... - === Starting performance test for gRPC client/server ==                                          
23:12:42.992 [main] INFO ... - Time spent for iteration 0 (100 records inserted/updated/deleted) is 52 Seconds                            
23:13:32.458 [main] INFO ... - Time spent for iteration 1 (100 records inserted/updated/deleted) is 49 Seconds                            
23:14:21.709 [main] INFO ... - Time spent for iteration 2 (100 records inserted/updated/deleted) is 49 Seconds                            
23:15:11.155 [main] INFO ... - Time spent for iteration 3 (100 records inserted/updated/deleted) is 49 Seconds                            
23:16:00.201 [main] INFO ... - Time spent for iteration 4 (100 records inserted/updated/deleted) is 49 Seconds                            
23:16:49.086 [main] INFO ... - Time spent for iteration 5 (100 records inserted/updated/deleted) is 48 Seconds                            
23:17:38.166 [main] INFO ... - Time spent for iteration 6 (100 records inserted/updated/deleted) is 49 Seconds                            
23:18:30.210 [main] INFO ... - Time spent for iteration 7 (100 records inserted/updated/deleted) is 52 Seconds                            
23:19:22.411 [main] INFO ... - Time spent for iteration 8 (100 records inserted/updated/deleted) is 52 Seconds                            
23:20:11.655 [main] INFO ... - Time spent for iteration 9 (100 records inserted/updated/deleted) is 49 Seconds                            
23:20:11.655 [main] INFO ... - === Performance test for gRPC client/server finished ==                        
                                                                                                                                          
# Used materials                                                                                                                          
                                                                                                                                          
https://www.postgresql.org/                                                                                                               
https://github.com/jOOQ/jOOQ                                                                                                              
https://spring.io/guides/gs/consuming-rest                                                                                                
https://awesomeopensource.com/project/yidongnan/grpc-spring-boot-starter                                                                  
