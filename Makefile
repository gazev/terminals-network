CLASSPATH :=/usr/share/java/po-uilib.jar:./prr-app/prr-app.jar:./prr-core/prr-core.jar

.PHONY = all clean install run test docs

all:
	(cd prr-core; make $(MFLAGS) all)
	(cd prr-app; make $(MFLAGS) all)

install:
	(cd prr-core; make $(MFLAGS) install)
	(cd prr-app; make $(MFLAGS) install)

run:
	java -cp $(CLASSPATH) -Dimport=test001.input prr.app.App

test:
	@./runtests.sh $(CLASSPATH) tests

docs:
	$(shell javadoc -d docs -sourcepath prr-core/src:prr-app/src -subpackages prr:prr.app)

clean:
	(cd prr-core; make $(MFLAGS) clean)
	(cd prr-app; make $(MFLAGS) clean)
	(cd tests; rm -rf *.diff *.outhyp)
	rm *.dat
