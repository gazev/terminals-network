CLASSPATH := /usr/share/java/po-uilib.jar:./prr-app/prr-app.jar:./prr-core/prr-core.jar
JARS := $(shell sed 's/\:/ /g' <<< $(CLASSPATH))

.PHONY = all clean install run

all:
	(cd prr-core; make $(MFLAGS) all)
	(cd prr-app; make $(MFLAGS) all)

clean:
	(cd prr-core; make $(MFLAGS) clean)
	(cd prr-app; make $(MFLAGS) clean)

install:
	(cd prr-core; make $(MFLAGS) install)
	(cd prr-app; make $(MFLAGS) install)

run: $(JARS)
	java -cp $(CLASSPATH) prr.app.App
